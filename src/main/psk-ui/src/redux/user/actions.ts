import { UserState, UPDATE_SESSION, USER_LOGIN } from './types';

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