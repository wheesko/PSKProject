import { Action } from 'redux';
import { ThunkAction } from 'redux-thunk';
import { AppState } from './redux';
import { LoginRequest } from './api/model/login-request';
import authenticationService from './api/authentication-service';
import { userLogin, userLogout } from './redux/user/actions';

// jwt.decode(response.headers.authorization.replace('Bearer ', '')
export const thunkLogin = (
	loginRequest: LoginRequest): ThunkAction<void, AppState, null, Action<string>> => async dispatch => {
	authenticationService.login(loginRequest).then(response => {
		console.log(response);
		dispatch(userLogin({
			email: loginRequest.email,
			loggedIn: true,
			token: response?.headers.authorization.replace('Bearer ', '')
		}));
	}).catch((error: any) => {
		console.log(error);
	});
};

export const thunkLogout = (): ThunkAction<void, AppState, null, Action<string>> => async dispatch => {
	authenticationService.logout().then(() => {
		dispatch(userLogout());
	}).catch((error: any) => {
		console.log(error);
	});
};