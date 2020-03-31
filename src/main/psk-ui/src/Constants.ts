export const TEAM_NAME: string = 'PSK_123';

export const DAY_EVENT_LIST_EMPTY = 'No events for this day';
//MENU LIST ITEM NAMES
export const CALENDAR_MENU_ITEM_NAME: string = 'My Calendar';
export const USER_MENU_ITEM_NAME: string = 'My Profile';
export const MY_TEAM_CALENDAR_MENU_ITEM_NAME: string = 'My Team Calendar';
export const INFO_MENU_ITEM_NAME: string = 'Info';
export const TEAMS_MENU_ITEM_NAME: string = 'Teams';
export const TEAM_MEMBERS_MENU_ITEM_NAME: string = 'Team Members';
export const TOPICS_MENU_ITEM_NAME: string = 'Topics';
export const TOPIC_TREE_MENU_ITEM_NAME: string = 'Topic Tree';
export const CREATE_NEW_TOPIC_MENU_ITEM_NAME: string = 'New Topic';

export const COMMENT: string = 'Comment';
export const EVENT_NAME: string = 'Event name';
export const ADD_NEW_LEARNING_EVENT = 'Add new learning event';
export const INPUT_EVENT_NAME: string = 'Input event name';
export const WARNING_EVENT_TIME_IS_REQUIRED: string = 'Event time is required!';
export const CHOOSE_TIME: string = 'Choose time';
export const ADD_LEARNING_EVENT_COMMENT: string = 'Add learning event comment';
export const SAVE_LEARNING_EVENT: string = 'Save learning event';
export const LEARNING_EVENT_SAVED = (eventName: string): string => {
		return `Learning event ${eventName} saved!`
	}
;
export const N_LEARNING_EVENTS = (eventAmount: number): string => {
	return eventAmount === 1 ? '1 learning event' : `${eventAmount} learning events`;
};
export const NEW_TOPIC_TITLE: string = 'Create your own topic';
export const MY_TEAM_CALENDAR = 'My Team Calendar';
export const MY_CALENDAR = 'My Calendar';
export const YOUR_EMPLOYEES = 'Your Employees';
export const NEW_TOPIC_SUBTITLE: string = 'Just a few fields and you\'ll be on your way studying whatever you want!';
export const TOPIC_NAME: string = 'Topic name';
export const DESCRIPTION: string = 'Description';
export const SUBMIT: string = 'Submit';
export const IS_THIS_A_SUBTOPIC: string = 'Is this a subtopic ?';
export const ADD_A_DESCRIPTION: string = 'Add some key info about the topic...';
export const INPUT_TOPIC_NAME: string = 'Input topic name';
export const SUBTOPIC_EXPLAINER: string = 'If this is a subtopic, it should be assigned to a parent topic';
// KEYS
export const KEY_TEAMS: string = 'teams';
export const KEY_CALENDAR: string = 'calendar';
export const KEY_MY_CALENDAR: string = 'my-calendar';
export const KEY_MEMBERS: string = 'members';
export const KEY_TEAM: string = 'team';
export const KEY_PROFILE: string = 'profile';
export const KEY_INFO: string = 'info';
export const KEY_TOPICS: string = 'topics';
export const KEY_TOPIC_TREE: string = 'topic-tree';
export const KEY_NEW_TOPIC: string = 'new-topic';