// constants for "sentences"
export const TEAM_NAME = 'PSK_123';
export const DAY_EVENT_LIST_EMPTY = 'No events for this day';
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
};
export const N_LEARNING_EVENTS = (eventAmount: number): string => {
	return eventAmount === 1 ? '1 learning event' : `${eventAmount} learning events`;
};
export const NEW_TOPIC_TITLE = 'Create your own topic';
export const MY_TEAM_CALENDAR = 'My Team Calendar';
export const MY_CALENDAR = 'My Calendar';
export const NEW_TOPIC_SUBTITLE = 'Just a few fields and you\'ll be on your way studying whatever you want!';
export const TOPIC_NAME = 'Topic name';
export const DESCRIPTION = 'Description';
export const SUBMIT = 'Submit';
export const IS_THIS_A_SUBTOPIC = 'Is this a subtopic ?';
export const ADD_A_DESCRIPTION = 'Add some key info about the topic...';
export const INPUT_TOPIC_NAME = 'Input topic name';
export const SUBTOPIC_EXPLAINER = 'If this is a subtopic, it should be assigned to a parent topic';
export const DONE = 'Done';
export const THIS_ACTION_CANNOT_BE_UNDONE = 'This action cannot be undone!';
export const EXPORT_TO_CSV = 'Export to CSV';
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
