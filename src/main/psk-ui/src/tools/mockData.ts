import { Worker } from '../models/worker';
import { Role } from '../models/role';
import { LearningEvent } from '../models/learningEvent';
import { Authority } from '../models/authority';
import { Employee } from '../models/employee';

export const roles: Role[] = [
	{ title: 'Java developer', color: 'blue' },
	{ title: 'Business analyst', color: 'gold' },
	{ title: 'Test engineer', color: 'green', },
	{ title: 'Software process manager', color: 'purple', },
	{ title: 'Front-end engineer', color: 'volcano' },
	{ title: 'Back-end engineer', color: 'lime' },
	{ title: 'Database engineer', color: 'geekblue' }];

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
		role: roles.find((role => role.title === "Back-end engineer")),
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
		role: roles.find((role => role.title === "Back-end engineer")),
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
export const myEmployees: Employee[] = [
	{
		id: 1,
		name: 'Povilas Tamosauskas',
		email: 'pt@psk123.com',
		role: roles.find((role => role.title === "Front-end engineer"))!.title,
		quarterConstraint: defaultQuarterConstraint,
		team: 'PSK_123',
		goals: [],
	}, {
		id: 2,
		name: 'Lukas Michnevic',
		email: 'lm@psk123.com',
		role: roles.find((role => role.title === "Back-end engineer"))!.title,
		quarterConstraint: defaultQuarterConstraint,
		team: 'PSK_123',
		goals: [],
	},
	{
		id: 3,
		name: 'Vytautas Rudys',
		email: 'vr@psk123.com',
		role: roles.find((role => role.title === "Back-end engineer"))!.title,
		quarterConstraint: defaultQuarterConstraint,
		team: 'PSK_123',
		goals: [],
	},
	{
		id: 4,
		name: 'Karolis Dijokas',
		email: 'kd@psk123.com',
		role: roles.find((role => role.title === "Database engineer"))!.title,
		quarterConstraint: defaultQuarterConstraint,
		team: 'PSK_123',
		goals: [],
	},
	{
		id: 5,
		name: 'Aurimas Golotylecas',
		email: 'ag@psk123.com',
		role: roles.find((role => role.title === "Back-end engineer"))!.title,
		quarterConstraint: defaultQuarterConstraint,
		team: 'PSK_123',
		goals: [],
	}];

export const learningEvents: LearningEvent[] = [
	{
		id: 0,
		name: 'Building apps with Redux',
		description: 'Some very very cool description',
		dateTimeAt: '',
		learned: false,
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
		learned: false,

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
		learned: false,

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