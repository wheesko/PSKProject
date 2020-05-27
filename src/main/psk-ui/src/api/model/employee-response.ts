import {Role} from "../../models/role";
import {Goal} from "../../models/goal";

export interface EmployeeResponse {
	id: number;
	name: string;
	team: string;
	email: string;
	role: { id: number; name: string; roleGoals: [] };
	managerId: number;
	surname: string;
	managedTeam: { id: number; name: string };
	workingTeam: { id: number; name: string };
	quarterLearningDayLimit: number;
	consecutiveLearningDayLimit: number;
	goals: [];
}