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
	{ name: 'junior', color: TagColors.MAGENTA },
	{ name: 'senior', color: TagColors.RED },
	{ name: 'database', color: TagColors.ORANGE },
	{ name: 'test', color: TagColors.VOLCANO },
	{ name: 'front', color: TagColors.GOLD },
	{ name: 'back', color: TagColors.LIME },
	{ name: 'developer', color: TagColors.GEEKBLUE },
	{ name: 'consultant', color: TagColors.GREEN },
	{ name: 'chief', color: TagColors.CYAN },
	{ name: 'engineer', color: TagColors.BLUE },
	{ name: 'executive', color: TagColors.PURPLE },
	{ name: 'manager', color: TagColors.BLUE },
	{ name: 'specialist', color: TagColors.GREEN },
	{ name: 'lead', color: TagColors.VOLCANO },
	{ name: 'designer', color: TagColors.GOLD },
	{ name: 'architect', color: TagColors.MAGENTA },
	{ name: 'recruiter', color: TagColors.ORANGE },
	{ name: 'strategist', color: TagColors.RED },
	{ name: 'analyst', color: TagColors.VOLCANO },
	{ name: 'head', color: TagColors.VOLCANO },
	{ name: 'director', color: TagColors.CYAN },
	{ name: 'expert', color: TagColors.BLUE },
	{ name: 'marketing', color: TagColors.LIME },
	{ name: 'business', color: TagColors.PURPLE }
];