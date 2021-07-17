const MAX_RESULTS = 100;

// todo HEADERS are even processed?


/*
 * Returns the linked issues by links count and project
 * @param {String} project
 * @param {Number} linksCount
 * @returns {Array} issues with links
 */
async function linkedIssues(jql, linksCount, startAt) {
    return await AP.request({
        url: '/rest/api/latest/search',
        headers: "{'keepAlive:', true}",
        type: 'POST',
        contentType: 'application/json',
        data: JSON.stringify({
            "jql": jql,
            "fields": ["summary", "issuelinks", 'reporter', 'assignee','status','resolution','created','updated'],
            "startAt": startAt,
            "maxResults": MAX_RESULTS
        })

    })
        .then(data => {
            data = JSON.parse(data.body);
            //console.log("LinkedIssues", jql);
            //console.log(data);

            let issues = data.issues
            if (jql.includes("LinksCount")) {
                issues.filter((issues) =>
                    issues.fields.issuelinks.length === linksCount
                );
            }
            return issues;
        })
        .catch((e) => console.log("linkedIssues request failed", e));
}

async function getIssue(issueId) {
    return await AP.request({
        url: '/rest/api/2/issue/' + issueId,
        headers: "{'keepAlive:', true}"

    })
        .then(data => {
            data = JSON.parse(data.body);
            return data;
        })
        .catch((e) => console.log("getIssue request failed", e))
}

/*
 * Returns the all available projects
 * @returns {Array} project tickers
 */
async function getProjects() {
    return await AP.request({
        url: '/rest/api/3/project/search',
        headers: "{'keepAlive:' true, 'Accept': 'application/json'}",
    })
        .then(data => {
            data = JSON.parse(data.body);
            const projects = [];
            data.values.forEach(project => {
                projects.push(project.key);
            })
            return projects;
        })
        .catch((e) => console.log('getProjects request failed', e))
}

async function getAllCustomFields() {
    return await AP.request({
        url: '/rest/api/3/issue/createmeta?'
            + 'expand=projects.issuetypes.fields',
        headers: "{'keepAlive:' true, 'Accept': 'application/json'}",
    })
        .then(data => {
            data = JSON.parse(data.body);
            const customFields = {};
            data.projects.forEach(projects => {
                projects.issuetypes.forEach(issuetypes => {
                    for (let key in issuetypes.fields) {
                        customFields[issuetypes.fields[key].name] =
                            [issuetypes.fields[key].name, issuetypes.fields[key].key,
                            issuetypes.fields[key].schema['type']];
                    }
                })
            })
            return customFields;

        })
        .catch((e) => console.log('getAllCustomFields request failed', e))
}

async function getTotals(jql) {
    return await AP.request({
        url: '/rest/api/latest/search',
        headers: "{'keepAlive:', true}",
        type: 'POST',
        contentType: 'application/json',
        data: JSON.stringify({
            "jql": jql,
            "fields": ["*none"],
            "maxResults": "0"
        })
    })
        .then(data => {
            console.log("TOTALS", data);
            data = JSON.parse(data.body);
            return data.total;
        })
        .catch((e) => console.log("getTotals request failed", e))
}

async function paginate(restFunction, args, startAt, totals) {
    let paginatedResponse = [];
    for (startAt; startAt < totals; startAt += MAX_RESULTS) {
        args[2] = startAt;
        let response = await restFunction.apply(this, args);
        Array.prototype.push.apply(paginatedResponse, response);
        if (paginatedResponse.length >= 10) {
            return [paginatedResponse, startAt + MAX_RESULTS];
        }
    }
    return [paginatedResponse, startAt + MAX_RESULTS];
}