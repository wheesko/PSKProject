export const ADD_EMPLOYEE = 'ADD_EMPLOYEE';
export const DELETE_EMPLOYEE = 'DELETE_EMPLOYEE';
export const UPDATE_EMPLOYEE = 'UPDATE_EMPLOYEE';
export const CLEAR_EMPLOYEES = 'CLEAR_EMPLOYEES';
export const LOAD_MY_EMPLOYEES = 'LOAD_MY_EMPLOYEES';

export interface Employee {
	id: number;
	name: string;
	team: string;
	quarterConstraint: number;
	role: string;
	goals: string[];
}

export interface MyEmployeesState {
	dataSource: Employee[];
}

interface AddEmployeeAction {
	type: typeof ADD_EMPLOYEE;
	payload: Employee;
}

interface DeleteEmployeeAction {
	type: typeof DELETE_EMPLOYEE;
	id: number; // EMPLOYEE ID
}

interface UpdateEmployee {
	type: typeof UPDATE_EMPLOYEE;
	employee: Employee;
}

interface ClearEmployeesAction {
	type: typeof CLEAR_EMPLOYEES;
}
interface LoadMyEmployeesAction {
	type: typeof LOAD_MY_EMPLOYEES;
	payload: Employee[];
}

export type MyEmployeesActionTypes = AddEmployeeAction | DeleteEmployeeAction | UpdateEmployee | ClearEmployeesAction | LoadMyEmployeesAction;
