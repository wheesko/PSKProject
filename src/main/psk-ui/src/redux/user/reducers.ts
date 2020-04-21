import { UPDATE_SESSION, USER_LOGIN, UserActionTypes, UserState } from './types';

const initialState: UserState = {
	loggedIn: false,
	userName: '',
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
	default:
		return state;
	}
}
