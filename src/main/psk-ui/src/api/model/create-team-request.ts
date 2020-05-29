export interface CreateTeamRequest {
	name: string;
	goal: string | null | undefined;
	managerId: number;
}