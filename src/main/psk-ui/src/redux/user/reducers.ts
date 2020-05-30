import { UPDATE_SESSION, USER_LOGIN, USER_LOGOUT, USER_TOKEN_UPDATE, UserActionTypes, UserState } from './types';
import { Authority } from '../../models/authority';

const initialState: UserState = {
	name: '',
	surname: '',
	loggedIn: false,
	email: '',
	token: '',
	role: { name: '', color: '', roleGoals: [], id: 0 },
	authority: Authority.UNASSIGNED,
	refreshToken: '',
	managedTeamId: 0,
	workingTeamId: 0,
	workerId: 0
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
