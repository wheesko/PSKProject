import { Action } from 'redux';
import { ThunkAction } from 'redux-thunk';
import jwt from 'jsonwebtoken';
import { AppState } from './redux';
import { LoginRequest } from './api/model/login-request';
import authenticationService from './api/authentication-service';
import { userLogin, userLogout } from './redux/user/actions';
import { addEmployee, loadMyEmployees } from './redux/my-employees/actions';
import { myEmployees } from './tools/mockData';
import { Employee } from './redux/my-employees/types';

export const thunkLogin = (
	loginRequest: LoginRequest): ThunkAction<void, AppState, null, Action<string>> => async dispatch => {
	authenticationService.login(loginRequest).then(response => {
		const decodedResponse = jwt.decode(response?.headers.authorization.replace('Bearer ', ''),
			{ json: true });

		dispatch(userLogin({
			email: loginRequest.email,
			loggedIn: true,
			token: response?.headers.authorization.replace('Bearer ', ''),
			authority: decodedResponse!.role[0].authority,
			role: { title: '', color: '' }
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

export const thunkAddEmployee = (newEmployee: Employee): ThunkAction<void, AppState, null, Action<string>> => async dispatch => {
	// send a POST request to the backend
	// ...
	// if successful add the employee to the redux store
	dispatch(addEmployee(newEmployee));
};
export const thunkLoadMyEmployees = (): ThunkAction<void, AppState, null, Action<string>> => async dispatch => {
	// will load employees from api endpoint
	dispatch(loadMyEmployees(myEmployees));
};