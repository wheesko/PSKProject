import { Authority } from '../../models/authority';
import { Role } from '../../models/role';

export interface LearningDayResponse {
	email: string;
	loggedIn: boolean;
	token: string;
	refreshToken: string;
	authority: Authority;
	role: Role;
}