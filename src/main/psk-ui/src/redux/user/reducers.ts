import { UPDATE_SESSION, USER_LOGIN, USER_LOGOUT, USER_TOKEN_UPDATE, UserActionTypes, UserState } from './types';
import { Authority } from '../../models/authority';

const initialState: UserState = {
	loggedIn: false,
	email: '',
	token: '',
	role: { title: '', color: '' },
	authority: Authority.UNASSIGNED,
	refreshToken: ''
};

export function userReducer(
	state = initialState,
	action: UserActionTypes
): UserState {
	switch (action.type) {
	case UPDATE_SESSION: {
		return {
			...state,
			...action.payload
		};
	}
	case USER_LOGIN: {
		return {
			...state,
			...action.payload
		};
	}
	case USER_LOGOUT: {
		return initialState;
	}
	case USER_TOKEN_UPDATE: {
		return {
			...state,
			...action.payload
		};
	}
	default:
		return state;
	}
}
