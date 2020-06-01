import { CancelSource } from './cancel-source';
import { RestService } from './rest-service';
import { AxiosResponse } from 'axios';
import { RegisterWorkerRequest } from './model/register-worker-request';
import { WorkerResponseModel } from './model/worker-response-model';
import { WorkerGoalCreateRequest } from './model/worker-goal-create-request';
import { UpdateLimitsRequest } from './model/update-limits-request';
import { LearningTopic } from "../models/learningTopic";
import { Employee } from '../models/employee';
import { getRoleColor } from '../tools/roleColorPicker';
import { EmployeeResponse } from './model/employee-response';
import { AddEmployeeRequest } from './model/add-employee-request';
import { ProfileResponseModel } from "./model/profile-response-model";

class WorkerService {
	private readonly restService: RestService;

	constructor(cancelSource: CancelSource = new CancelSource()) {
		this.restService = cancelSource.service;
	}

	public registerWorker = (registerWorkerRequest: RegisterWorkerRequest): Promise<AxiosResponse | null> => {
		return this.restService.put<void>('/workers/registerWorker', registerWorkerRequest);
	};

	public getWorker = (id: number): Promise<WorkerResponseModel> => {
		return this.restService.get<WorkerResponseModel>(`/workers/get/${id}`)
			.then(response => response.data);
	};

	public getOwnProfile = (): Promise<ProfileResponseModel> => {
		return this.restService.get<ProfileResponseModel>(`/workers/get/profile`)
			.then(response => response.data);
	};

	public assignGoal = (goal: WorkerGoalCreateRequest): Promise<void> => {
		return this.restService.post<void>('/workerGoals/create', goal)
			.then(response => response.data);
	};

	public updateLimits = (limitsRequest: UpdateLimitsRequest, id: number): Promise<void> => {
		return this.restService.put<void>(`/workers/updateWorkerLimits/${id}`, limitsRequest)
			.then(response => response.data);
	};

	public getOwnGoals = (): Promise<LearningTopic[]> => {
		return this.restService.get<LearningTopic[]>(`/workerGoals/getOwn`)
			.then(response => response.data);
	};

	public getEmployees = (): Promise<Employee[]> => {
		return this.restService.get<EmployeeResponse>('/workers/getEmployees',).then((response: AxiosResponse) => {
			return response.data.map((record: EmployeeResponse) => {
				return {
					consecutiveLearningDayLimit: record?.consecutiveLearningDayLimit,
					email: record?.email,
					goals: [],
					id: record?.id,
					managedTeam: record?.managedTeam,
					managerId: record?.managerId,
					name: record?.name,
					quarterLearningDayLimit: record?.quarterLearningDayLimit,
					role: record.role === null ? { name: '', color: '' } : {
						name: record?.role.name,
						color: getRoleColor(record?.role.name)
					},
					surname: record?.surname,
					team: record?.workingTeam.name,
					workingTeam: record?.workingTeam,
				};
			});
		});
	};
	public getColleagues = (): Promise<Employee[]> => {
		return this.restService.get<EmployeeResponse>('/workers/getColleagues',).then((response: AxiosResponse) => {
			return response.data.map((record: EmployeeResponse) => {
				return {
					consecutiveLearningDayLimit: record?.consecutiveLearningDayLimit,
					email: record?.email,
					goals: [],
					id: record?.id,
					managedTeam: record?.managedTeam,
					managerId: record?.managerId,
					name: record?.name,
					quarterLearningDayLimit: record?.quarterLearningDayLimit,
					role: record.role === null ? { name: '', color: '' } : {
						name: record?.role.name,
						color: getRoleColor(record?.role.name)
					},
					surname: record?.surname,
					team: record?.workingTeam.name,
					workingTeam: record?.workingTeam,
				};
			});
		});
	};

	public addEmployee = (addEmployeeRequest: AddEmployeeRequest): Promise<AxiosResponse | null> => {
		return this.restService.post<void>('/workers/create', addEmployeeRequest);
	};

	public getMembersOfTeam = (id: number): Promise<any> => {
		return this.restService.get<any>(`/teams/get/${id}`)
			.then(response => response.data);
	}

}

const workerService = new WorkerService();

export { WorkerService };
export { workerService as default };
