// Describing the shape of the system's slice of state
export interface UserState {
	loggedIn: boolean;
	email: string;
	token: string;
}

// Describing the different ACTION NAMES available
export const UPDATE_SESSION = 'UPDATE_SESSION';
export const USER_LOGIN = 'USER_LOGIN';
export const USER_LOGOUT = 'USER_LOGOUT';

interface UpdateSessionAction {
	type: typeof UPDATE_SESSION;
	payload: UserState;
}

interface LoginAction {
	type: typeof USER_LOGIN;
	payload: UserState;
}

interface LogoutAction {
	type: typeof USER_LOGOUT;
	payload: UserState;
}

export type UserActionTypes = UpdateSessionAction | LoginAction | LogoutAction;
