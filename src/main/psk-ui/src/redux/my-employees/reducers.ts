import {
	ADD_EMPLOYEE,
	CLEAR_EMPLOYEES,
	DELETE_EMPLOYEE,
	Employee,
	LOAD_MY_EMPLOYEES,
	MyEmployeesActionTypes,
	MyEmployeesState,
	UPDATE_EMPLOYEE
} from './types';

const initialState: MyEmployeesState = {
	dataSource: [],
};

export function myEmployeesReducer(
	state = initialState,
	action: MyEmployeesActionTypes
): MyEmployeesState {
	switch (action.type) {
	case LOAD_MY_EMPLOYEES: {
		return { dataSource: action.payload };
	}
	case ADD_EMPLOYEE: {
		return {
			...state,
			dataSource:[...state.dataSource,action.payload],
		};
	}
	case UPDATE_EMPLOYEE: {
		return {
			dataSource: state.dataSource.map((employee: Employee) => {
				return employee.id === action.employee.id ? action.employee : employee;
			})
		};
	}
	case DELETE_EMPLOYEE: {
		return {
			dataSource: state.dataSource.filter((employee: Employee) =>
				employee.id !== action.id
			)
		};
	}
	case CLEAR_EMPLOYEES:
		return initialState;
	default:
		return state;
	}
}
