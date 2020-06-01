import { CancelSource } from './cancel-source';
import { RestService } from './rest-service';
import { AxiosResponse } from 'axios';
import { CreateTeamRequest } from './model/create-team-request';
import { LearningEvent } from "../models/learningEvent";
import { TeamResponse } from './model/team-response';



class TeamService {
	private readonly restService: RestService;

	constructor(cancelSource: CancelSource = new CancelSource()) {
		this.restService = cancelSource.service;
	}
	public createTeam = (createTeamRequest: CreateTeamRequest): Promise<AxiosResponse | null> => {
		return this.restService.post<void>('/teams/create', createTeamRequest);
	};

	public getAllTeams = (): Promise<TeamResponse[]> => {
		return this.restService
			.get<TeamResponse[]>(
				`/teams/getAll`
			)
			.then((response: AxiosResponse<TeamResponse[]>) => {
				return response.data;
			});
	};

}

const teamService = new TeamService();

export { TeamService };
export { teamService as default };
