import { Role } from './role';
import { Goal } from './goal';

export interface Employee {
	id: number;
	name: string;
	team: string;
	email: string;
	role: Role;
	managerId: number;
	surname: string;
	managedTeam: { id: number; name: string };
	workingTeam: { id: number; name: string };
	quarterLearningDayLimit: number;
	consecutiveLearningDayLimit: number;
	goals: Goal[];
}