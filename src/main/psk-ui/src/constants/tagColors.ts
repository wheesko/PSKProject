import { Tag } from 'antd';
import { Role } from '../models/role';

export enum TagColors {
	MAGENTA = 'magenta',
	RED = 'red',
	VOLCANO = 'volcano',
	ORANGE = 'orange',
	GOLD = 'gold',
	LIME = 'lime',
	GREEN = 'green',
	CYAN = 'cyan',
	BLUE = 'blue',
	GEEKBLUE = 'geekblue',
	PURPLE = 'purple'
}

// dictionary of common roles to colors
export const roleToColorDictionary: Role[] = [
	{ name: 'junior', color: TagColors.MAGENTA, roleGoals: [], id: 0 },
	{ name: 'senior', color: TagColors.RED, roleGoals: [], id: 1 },
	{ name: 'database', color: TagColors.ORANGE, roleGoals: [], id: 2 },
	{ name: 'test', color: TagColors.VOLCANO, roleGoals: [], id: 3 },
	{ name: 'front', color: TagColors.GOLD, roleGoals: [], id: 4 },
	{ name: 'back', color: TagColors.LIME, roleGoals: [], id: 5 },
	{ name: 'developer', color: TagColors.GEEKBLUE, roleGoals: [], id: 6 },
	{ name: 'consultant', color: TagColors.GREEN, roleGoals: [], id: 7 },
	{ name: 'chief', color: TagColors.CYAN, roleGoals: [], id: 8 },
	{ name: 'engineer', color: TagColors.BLUE, roleGoals: [], id: 9 },
	{ name: 'executive', color: TagColors.PURPLE, roleGoals: [], id: 10 },
	{ name: 'manager', color: TagColors.BLUE, roleGoals: [], id: 11 },
	{ name: 'specialist', color: TagColors.GREEN, roleGoals: [], id: 12 },
	{ name: 'lead', color: TagColors.VOLCANO, roleGoals: [], id: 13 },
	{ name: 'designer', color: TagColors.GOLD, roleGoals: [], id: 14 },
	{ name: 'architect', color: TagColors.MAGENTA, roleGoals: [], id: 15 },
	{ name: 'recruiter', color: TagColors.ORANGE, roleGoals: [], id: 16 },
	{ name: 'strategist', color: TagColors.RED, roleGoals: [], id: 17 },
	{ name: 'analyst', color: TagColors.VOLCANO, roleGoals: [], id: 18 },
	{ name: 'head', color: TagColors.VOLCANO, roleGoals: [], id: 19 },
	{ name: 'director', color: TagColors.CYAN, roleGoals: [], id: 20 },
	{ name: 'expert', color: TagColors.BLUE, roleGoals: [], id: 21 },
	{ name: 'marketing', color: TagColors.LIME, roleGoals: [], id: 22 },
	{ name: 'business', color: TagColors.PURPLE, roleGoals: [], id: 23 }
];