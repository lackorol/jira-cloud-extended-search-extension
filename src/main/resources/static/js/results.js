function sendFirstTen(firstTen, totals) {
    console.time("sendFirstTen");
    $.ajax({
        type: 'POST',
        url: '/receiveFirstTenIssues?totals='+totals,
        data: JSON.stringify(firstTen),
        success: function (response) {
            $('#extensionSearchTable').html($(response).find('#extensionSearchTable').html());
            $('#extensionSearchTablePagination').html($(response).find('#extensionSearchTablePagination').html());
            //$('body').html(response);
            console.log('Successfully sent first ten issues.');
        },
        error: function (e) {
            console.log('Error during sending first ten issues.', e);
        },
        contentType: "application/json; charset=utf-8",
    })
    console.timeEnd("sendFirstTen");
}

function sendNextIssues(next) {
    console.time("sendNextIssues");
    console.log(next);
    $.ajax({
        type: 'POST',
        url: '/receiveNextIssues',
        data: JSON.stringify(next),
        success: function (response) {
            console.log('Successfully sent next issues.');
        },
        error: function (e) {
            console.log('Error during sending next issues.', e);
        },
        contentType: "application/json; charset=utf-8",
    })
    console.timeEnd("sendNextIssues");
}

