const EQUALS = "=";
const NOT_EQUALS = "!=";
const LIKE = "~";
const NOT_LIKE = "!~";
const GREATER_THEN = ">";
const LESS_THEN = "<";
const GREATER_THEN_EQUALS = ">=";
const LESS_THEN_EQUALS = "<=";
const IS = "is";
const IS_NOT = "is not";
const IN = "in";
const NOT_IN = "not in";
const WAS = "was";
const WAS_NOT = "was not";
const WAS_IN = "was in";
const WAS_NOT_IN = "was not in";
const CHANGED = "changed";

const TYPES_GROUP1 = ['number','date', 'datetime']
const TYPES_GROUP2 = ['array', 'option', 'option-with-child','user', 'group'];
const TYPES = [...TYPES_GROUP1, ...TYPES_GROUP2, 'string', 'timetracking', 'sd-servicelevelagreement'];

const OPERATORS = [
    EQUALS, NOT_EQUALS, LIKE, NOT_LIKE, GREATER_THEN,
    LESS_THEN, GREATER_THEN_EQUALS, LESS_THEN_EQUALS,
    IS, IS_NOT, IN, NOT_IN, WAS, WAS_NOT, WAS_IN, WAS_NOT_IN, CHANGED
];
const OPERATORS_STRING = [LIKE, NOT_LIKE, IS_NOT, IS];

const OPERATORS_GROUP1 = [
    EQUALS, NOT_EQUALS, LESS_THEN_EQUALS, GREATER_THEN_EQUALS,
    LESS_THEN, GREATER_THEN, IS_NOT, IS, NOT_IN, IN
];

const OPERATORS_GROUP2 = [EQUALS, NOT_EQUALS, IS_NOT, IS, NOT_IN, IN];

const OPERATORS_SD_SERVICELEVELAGREEMENT = [
    EQUALS, NOT_EQUALS, LESS_THEN_EQUALS,
    GREATER_THEN_EQUALS, LESS_THEN, GREATER_THEN
];

const OPERATORS_TIME_TRACKING = OPERATORS;

const AND = "AND";
const OR = "OR";
const ORDER_BY = "ORDER BY";

const CONNECTORS = [AND, OR, ORDER_BY];


function addJqlErrorOrJqlGoodIntoSearchbar() {
    if ($('#jql-extension-input-search-bar').val = '' &&
        !$('.jql-indicator').hasClass('jqlerror')) {
        $('.jql-indicator').removeClass('jqlgood')
        $('.jql-indicator').addClass('jqlerror')
    } else {
        $('.jql-indicator').removeClass('jqlerror');
        if (!$('.jql-indicator').hasClass('jqlgood')) {
            $('.jql-indicator').addClass('jqlgood');
        }
    }
}

function parseSearchQuery(query) {

}