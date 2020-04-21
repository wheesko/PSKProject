export const TEAM_NAME = 'PSK_123';

export const DAY_EVENT_LIST_EMPTY = 'No events for this day';
//MENU LIST ITEM NAMES
export const CALENDAR_MENU_ITEM_NAME = 'My Calendar';
export const USER_MENU_ITEM_NAME = 'My Profile';
export const MY_TEAM_CALENDAR_MENU_ITEM_NAME = 'My Team Calendar';
export const INFO_MENU_ITEM_NAME = 'Info';
export const TEAMS_MENU_ITEM_NAME = 'Teams';
export const TEAM_MEMBERS_MENU_ITEM_NAME = 'Team Members';
export const TOPICS_MENU_ITEM_NAME = 'Topics';
export const TOPIC_TREE_MENU_ITEM_NAME = 'Topic Tree';
export const CREATE_NEW_TOPIC_MENU_ITEM_NAME = 'New Topic';

export const COMMENT = 'Comment';
export const EVENT_NAME = 'Event name';
export const ADD_NEW_LEARNING_EVENT = 'Add new learning event';
export const INPUT_EVENT_NAME = 'Input event name';
export const WARNING_EVENT_TIME_IS_REQUIRED = 'Event time is required!';
export const CHOOSE_TIME = 'Choose time';
export const ADD_LEARNING_EVENT_COMMENT = 'Add learning event comment';
export const SAVE_LEARNING_EVENT = 'Save learning event';
export const LEARNING_EVENT_SAVED = (eventName: string): string => {
	return `Learning event ${eventName} saved!`;
}
;
export const N_LEARNING_EVENTS = (eventAmount: number): string => {
	return eventAmount === 1 ? '1 learning event' : `${eventAmount} learning events`;
};
export const NEW_TOPIC_TITLE = 'Create your own topic';
export const MY_TEAM_CALENDAR = 'My Team Calendar';
export const MY_CALENDAR = 'My Calendar';
export const YOUR_EMPLOYEES = 'Your Employees';
export const NEW_TOPIC_SUBTITLE = 'Just a few fields and you\'ll be on your way studying whatever you want!';
export const TOPIC_NAME = 'Topic name';
export const DESCRIPTION = 'Description';
export const SUBMIT = 'Submit';
export const IS_THIS_A_SUBTOPIC = 'Is this a subtopic ?';
export const ADD_A_DESCRIPTION = 'Add some key info about the topic...';
export const INPUT_TOPIC_NAME = 'Input topic name';
export const SUBTOPIC_EXPLAINER = 'If this is a subtopic, it should be assigned to a parent topic';
// KEYS
export const KEY_TEAMS = 'teams';
export const KEY_CALENDAR = 'calendar';
export const KEY_MY_CALENDAR = 'my-calendar';
export const KEY_MEMBERS = 'members';
export const KEY_TEAM = 'team';
export const KEY_PROFILE = 'profile';
export const KEY_INFO = 'info';
export const KEY_TOPICS = 'topics';
export const KEY_TOPIC_TREE = 'topic-tree';
export const KEY_NEW_TOPIC = 'new-topic';
//LIST ITEM NAMES
export const CALENDAR_ITEM_NAME = 'My Calendar';
export const USER_ITEM_NAME = 'My Profile';

//ROLES
export enum Roles {
    ROLE_LEAD = 'LEAD',
    ROLE_WORKER = 'WORKER'
}

export enum AlertConstants {
    SUCCESS = 'Success',
    ERROR = 'Error',
    CLEAR = 'Clear'
}
