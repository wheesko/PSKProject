import { CancelSource } from './cancel-source';
import { RestService } from './rest-service';
import { AxiosResponse } from 'axios';
import { CreateTeamRequest } from './model/create-team-request';



class TeamService {
	private readonly restService: RestService;

	constructor(cancelSource: CancelSource = new CancelSource()) {
		this.restService = cancelSource.service;
	}
	public createTeam = (createTeamRequest: CreateTeamRequest): Promise<AxiosResponse | null> => {
		return this.restService.post<void>('/teams/create', createTeamRequest);
	};

}

const teamService = new TeamService();

export { TeamService };
export { teamService as default };
