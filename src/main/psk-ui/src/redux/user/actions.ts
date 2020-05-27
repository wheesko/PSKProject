import {UserState, UPDATE_SESSION, USER_LOGIN, USER_LOGOUT, USER_TOKEN_UPDATE, USER_REGISTER} from './types';

export function updateSession(newSession: UserState) {
	return {
		type: UPDATE_SESSION,
		payload: newSession
	};
}

export function userLogin(newState: UserState) {
	return {
		type: USER_LOGIN,
		payload: newState
	};
}

export function userLogout() {
	return {
		type: USER_LOGOUT
	};
}

export function userRegister(newState: UserState) {
	return {
		type: USER_REGISTER,
		payload: newState
	};
}

export function updateToken(newState: { token: string }) {
	return {
		type: USER_TOKEN_UPDATE,
		payload: newState
	};
}