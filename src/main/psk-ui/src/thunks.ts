import { Action } from 'redux';
import { ThunkAction } from 'redux-thunk';
import jwt from 'jsonwebtoken';
import { AppState } from './redux';
import { LoginRequest } from './api/model/login-request';
import authenticationService from './api/authentication-service';
import {userLogin, userLogout, userRegister} from './redux/user/actions';
import notificationService, { NotificationType } from './service/notification-service';
import { RegisterWorkerRequest } from './api/model/register-worker-request';
import workerService from './api/worker-service';

export const thunkLogin = (
	loginRequest: LoginRequest): ThunkAction<void, AppState, null, Action<string>> => async dispatch => {
	authenticationService.login(loginRequest).then(response => {
		console.log(response);
		const decodedResponse = jwt.decode(response?.headers.authorization.replace('Bearer ', ''),
			{ json: true });

		dispatch(userLogin({
			email: loginRequest.email,
			loggedIn: true,
			token: response?.headers.authorization.replace('Bearer ', ''),
			refreshToken: response?.headers.refreshtoken.replace('Bearer ', ''),
			authority: decodedResponse!.role[0].authority,
			role: { title: '', color: '' }
		}));

		notificationService.notify({
			notificationType: NotificationType.SUCCESS,
			message: 'Logged in successfully'
		});
	}).catch(() => {
		notificationService.notify({
			notificationType: NotificationType.ERROR,
			message: 'Login unsuccessful',
			description: 'Please check your credentials'
		});
	});
};

export const thunkLogout = (): ThunkAction<void, AppState, null, Action<string>> => async dispatch => {
	authenticationService.logout().then(() => {
		dispatch(userLogout());
	}).catch((error: any) => {
		console.log(error);
	});
};

export const thunkRegister = (registerRequest: RegisterWorkerRequest): ThunkAction<void, AppState, null, Action<string>> => async dispatch => {
	workerService.registerWorker(registerRequest).then(response  => {
		notificationService.notify({
			notificationType: NotificationType.SUCCESS,
			message: 'Registration successful'
		});
		dispatch(userLogout());
	});
};
