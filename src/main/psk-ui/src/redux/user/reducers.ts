import { UPDATE_SESSION, USER_LOGIN, USER_LOGOUT, UserActionTypes, UserState } from './types';

const initialState: UserState = {
	loggedIn: false,
	email: '',
	token: '',
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
	default:
		return state;
	}
}
