// Describing the shape of the system's slice of state
import { Role } from '../../models/role';
import { Authority } from '../../models/authority';

export interface UserState {
	loggedIn: boolean;
	email: string;
	token: string;
	role: Role;
	authority: Authority;
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
}

export type UserActionTypes = UpdateSessionAction | LoginAction | LogoutAction;
