import { Action } from 'redux';
import { ThunkAction } from 'redux-thunk';
import { AppState } from './redux';
import { LoginRequest } from './api/model/login-request';
import authenticationService from './api/authentication-service';
import { userLogin } from './redux/user/actions';

// jwt.decode(response.headers.authorization.replace('Bearer ', '')
export const thunkLogin = (
	loginRequest: LoginRequest): ThunkAction<void, AppState, null, Action<string>> => async dispatch => {
	authenticationService.login(loginRequest).then(response => {
		dispatch(userLogin({
			userName: loginRequest.userName,
			loggedIn: true,
			token: response?.headers.authorization.replace('Bearer ', '')
		}));
	}).catch((error: any) => {
		console.log(error);
	});
};