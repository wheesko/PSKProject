import { Worker } from '../models/worker';
import { Role } from '../models/role';
import { LearningEvent } from '../models/learningEvent';

export const roles: Role[] = [
	{ id: 0, title: 'Java developer', description: '', color: 'blue' },
	{ id: 1, title: 'Business analyst', description: '', color: 'gold' },
	{ id: 2, title: 'Test engineer', color: 'green', description: '' },
	{ id: 3, title: 'Software process manager', color: 'purple', description: '' },
	{ id: 4, description: '', title: 'Front-end engineer', color: 'volcano' },
	{ id: 5, description: '', title: 'Back-end engineer', color: 'lime' },
	{ id: 6, description: '', title: 'Database engineer', color: 'geekblue' }];
export const workerList: Worker[] = [
	{
		id: 1,
		name: 'Povilas',
		surname: 'Tamosauskas',
		role: roles.find(role => role.id === 4),
		quarterConstraint: 3,
		team: 'PSK_123',
		// learnedTopics:
		goals: [],
		manager: 'Vytautas Rudys',
		learnedTopics: [],
		learningEvents: [],
		icon: null,
	}, {
		id: 2,
		name: 'Lukas',
		surname: 'Michnevic',
		role: roles.find(role => role.id === 5),
		quarterConstraint: 3,
		team: 'PSK_123',
		// learnedTopics:
		goals: [],
		manager: 'Vytautas Rudys',
		learnedTopics: [],
		learningEvents: [],
		icon: null,
	},
	{
		id: 3,
		name: 'Vytautas',
		surname: 'Rudys',
		role: roles.find(role => role.id === 5),
		quarterConstraint: 3,
		team: 'PSK_123',
		// learnedTopics:
		goals: [],
		manager: '',
		learnedTopics: [],
		learningEvents: [],
		icon: null,
	},
	{
		id: 4,
		name: 'Karolis',
		surname: 'Dijokas',
		role: roles.find(role => role.id === 6),
		quarterConstraint: 3,
		team: 'PSK_123',
		// learnedTopics:
		goals: [],
		manager: 'Vytautas Rudys',
		learnedTopics: [],
		learningEvents: [],
		icon: null,
	}];

export const learningEvents: LearningEvent[] = [
	{
		id: 0,
		name: 'Building apps with Redux',
		description: 'Some very very cool description',
		timeFrom: null,
		timeTo: null,
		learningTopic: {
			id: 0,
			name: 'Web topic',
			description: 'string',
			color: 'red',
			parentTopic: null,
			createdBy: 0,
		},
		assignedWorker: null,
	},
	{
		id: 1,
		name: 'JPA basics',
		description: 'Some very very cool description',
		timeFrom: null,
		timeTo: null,
		learningTopic: {
			id: 1,
			name: 'Java topic',
			description: 'string',
			color: 'blue',
			parentTopic: null,
			createdBy: 0,
		},
		assignedWorker: null,

	},
	{
		id: 2,
		name: 'Automated testing',
		description: 'Learning automated testing with selenium and webdriver',
		timeFrom: null,
		timeTo: null,
		learningTopic: {
			id: 2,
			name: 'Testing',
			description: 'string',
			color: 'green',
			parentTopic: null,
			createdBy: 0,
		},
		assignedWorker: null,

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