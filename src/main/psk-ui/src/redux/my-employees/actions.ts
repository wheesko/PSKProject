import {
	ADD_EMPLOYEE,
	CLEAR_EMPLOYEES,
	DELETE_EMPLOYEE,
	Employee,
	LOAD_MY_EMPLOYEES,
	MyEmployeesState,
	UPDATE_EMPLOYEE
} from './types';

export function addEmployee(newEmployee: Employee) {
	return {
		type: ADD_EMPLOYEE,
		payload: newEmployee
	};
}

export function updateEmployee(newEmployee: Employee) {
	return {
		type: UPDATE_EMPLOYEE,
		payload: newEmployee
	};
}

export function deleteEmployee(id: number) {
	return {
		type: DELETE_EMPLOYEE,
		id: id
	};
}

export function loadMyEmployees(employees: Employee[]) {
	return {
		type: LOAD_MY_EMPLOYEES,
		payload: employees
	};
}
export function clearMyEmployees() {
	return { type: CLEAR_EMPLOYEES };
}
