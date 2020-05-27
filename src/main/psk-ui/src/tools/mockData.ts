import { Worker } from '../models/worker';
import { Role } from '../models/role';
import { LearningEvent } from '../models/learningEvent';
import { Authority } from '../models/authority';
import { Employee } from '../models/employee';
import { getRoleColor } from './roleColorPicker';

export const roles: Role[] = [
	{ name: 'Java developer', color: 'blue' },
	{ name: 'Business analyst', color: 'gold' },
	{ name: 'Test engineer', color: 'green', },
	{ name: 'Software process manager', color: 'purple', },
	{ name: 'Front-end engineer', color: 'volcano' },
	{ name: 'Back-end engineer', color: 'lime' },
	{ name: 'Database engineer', color: 'geekblue' }];

export const defaultQuarterConstraint = 3;

export const workerList: Worker[] = [
	{
		id: 1,
		name: 'Povilas',
		surname: 'Tamosauskas',
		role: roles[4],
		quarterConstraint: 3,
		team: 'PSK_123',
		// learnedTopics:
		goals: [],
		manager: 'Vytautas Rudys',
		learnedTopics: [],
		learningEvents: [],
		icon: null,
		authority: Authority.UNASSIGNED
	}, {
		id: 2,
		name: 'Lukas',
		surname: 'Michnevic',
		role: roles.find((role => role.name === 'Back-end engineer')),
		quarterConstraint: defaultQuarterConstraint,
		team: 'PSK_123',
		// learnedTopics:
		goals: [],
		manager: 'Vytautas Rudys',
		learnedTopics: [],
		learningEvents: [],
		icon: null,
		authority: Authority.UNASSIGNED
	},
	{
		id: 3,
		name: 'Vytautas',
		surname: 'Rudys',
		role: roles.find((role => role.name === 'Back-end engineer')),
		quarterConstraint: defaultQuarterConstraint,
		team: 'PSK_123',
		// learnedTopics:
		goals: [],
		manager: '',
		learnedTopics: [],
		learningEvents: [],
		icon: null,
		authority: Authority.UNASSIGNED
	},
	{
		id: 4,
		name: 'Karolis',
		surname: 'Dijokas',
		role: roles[6],
		quarterConstraint: defaultQuarterConstraint,
		team: 'PSK_123',
		// learnedTopics:
		goals: [],
		manager: 'Vytautas Rudys',
		learnedTopics: [],
		learningEvents: [],
		icon: null,
		authority: Authority.UNASSIGNED
	}];
export const psk123Team = { id: 1, name: 'PSK_123' };
export const myEmployees: Employee[] = [
	{
		consecutiveLearningDayLimit: 5,
		email: 'email1@mail.com',
		goals: [],
		id: 1,
		managedTeam: { id: 0, name: '' },
		workingTeam: psk123Team,
		managerId: 3,
		name: 'Povilas',
		quarterLearningDayLimit: 3,
		role: { name: 'Front-end Engineer', color: getRoleColor('Front-end Engineer') },
		surname: 'Tamosauskas',
		team: psk123Team.name,
	}, {
		consecutiveLearningDayLimit: 5,
		email: 'email2@mail.com',
		goals: [],
		managedTeam: { id: 0, name: '' },
		workingTeam: psk123Team,
		managerId: 3,
		name: 'Lukas',
		quarterLearningDayLimit: 3,
		role: { name: 'Back-end engineer', color: getRoleColor('Back-end engineer') },
		surname: 'Michnevic',
		team: psk123Team.name,
		id: 2,
	},
	{
		consecutiveLearningDayLimit: 5,
		email: 'email3@mail.com',
		goals: [],
		managedTeam: psk123Team,
		workingTeam: psk123Team,
		managerId: -1,
		quarterLearningDayLimit: 3,
		role: { name: 'Back-end engineer', color: getRoleColor('Back-end engineer') },
		surname: 'Rudys',
		team: psk123Team.name,
		id: 3,
		name: 'Vytautas',
	},
	{
		consecutiveLearningDayLimit: 5,
		email: 'email4@mail.com',
		goals: [],
		managedTeam: { id: 0, name:'' },
		workingTeam: psk123Team,
		managerId: -1,
		quarterLearningDayLimit: 3,
		role: { name: 'Database engineer', color: getRoleColor('Database engineer') },
		surname: 'Dijokas',
		team: psk123Team.name,
		id: 4,
		name: 'Karolis',
	},
	{
		consecutiveLearningDayLimit: 5,
		email: 'email5@mail.com',
		goals: [],
		managedTeam: { id: 0, name:'' },
		workingTeam: psk123Team,
		managerId: -1,
		quarterLearningDayLimit: 3,
		role: { name: 'Backend engineer', color: getRoleColor('Backend engineer') },
		surname: 'Golotylecas',
		team: psk123Team.name,
		id: 5,
		name: 'Aurimas',
	}];

export const learningEvents: LearningEvent[] = [
	{
		id: 0,
		name: 'Building apps with Redux',
		description: 'Some very very cool description',
		dateTimeAt: '',
		topic: {
			id: 0,
			name: 'Web topic',
			description: 'string',
			color: 'red',
			parentTopic: null,
			createdBy: 0,
		},
		assignedWorker: null,
		comment: ''
	},
	{
		id: 1,
		name: 'JPA basics',
		description: 'Some very very cool description',
		dateTimeAt: '',
		topic: {
			id: 1,
			name: 'Java topic',
			description: 'string',
			color: 'blue',
			parentTopic: null,
			createdBy: 0,
		},
		assignedWorker: null,
		comment: ''
	},
	{
		id: 2,
		name: 'Automated testing',
		description: 'Learning automated testing with selenium and webdriver',
		dateTimeAt: '',
		topic: {
			id: 2,
			name: 'Testing',
			description: 'string',
			color: 'green',
			parentTopic: null,
			createdBy: 0,
		},
		assignedWorker: null,
		comment: ''
	}
];

export const teams = [{
	teamName: 'Alpha',
	manager: 'Fictional Manager1',
	employeeAmount: 3,
	planningAmount: 4,
},
{
	teamName: 'Beta',
	manager: 'Fictional Manager1',
	employeeAmount: 5,
	planningAmount: 7,
}];