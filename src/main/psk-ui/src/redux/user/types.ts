// Describing the shape of the system's slice of state
import { Role } from '../../models/role';
import { Authority } from '../../models/authority';

export interface UserState {
	loggedIn: boolean;
	email: string;
	token: string;
	role: Role;
	authority: Authority;
	refreshToken: string;
	managedTeamId: number;
	workingTeamId: number;
	workerId: number;
	name: string;
	surname: string;
}

// Describing the different ACTION NAMES available
export const UPDATE_SESSION = 'UPDATE_SESSION';
export const USER_LOGIN = 'USER_LOGIN';
export const USER_LOGOUT = 'USER_LOGOUT';
export const USER_TOKEN_UPDATE = 'USER_TOKEN_UPDATE';
export const USER_REGISTER = 'USER_REGISTER';

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

interface UserTokenUpdateAction {
	type: typeof USER_TOKEN_UPDATE;
	payload: UserState;
}
interface RegisterAction {
	type: typeof USER_REGISTER;
	payload: UserState;
}

export type UserActionTypes = UpdateSessionAction | LoginAction | LogoutAction | UserTokenUpdateAction | RegisterAction;
