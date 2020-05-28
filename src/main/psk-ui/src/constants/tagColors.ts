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
	{ title: 'junior', color: TagColors.MAGENTA },
	{ title: 'senior', color: TagColors.RED },
	{ title: 'database', color: TagColors.ORANGE },
	{ title: 'test', color: TagColors.VOLCANO },
	{ title: 'front', color: TagColors.GOLD },
	{ title: 'back', color: TagColors.LIME },
	{ title: 'developer', color: TagColors.GEEKBLUE },
	{ title: 'consultant', color: TagColors.GREEN },
	{ title: 'chief', color: TagColors.CYAN },
	{ title: 'engineer', color: TagColors.BLUE },
	{ title: 'executive', color: TagColors.PURPLE },
	{ title: 'manager', color: TagColors.BLUE },
	{ title: 'specialist', color: TagColors.GREEN },
	{ title: 'lead', color: TagColors.VOLCANO },
	{ title: 'designer', color: TagColors.GOLD },
	{ title: 'architect', color: TagColors.MAGENTA },
	{ title: 'recruiter', color: TagColors.ORANGE },
	{ title: 'strategist', color: TagColors.RED },
	{ title: 'analyst', color: TagColors.VOLCANO },
	{ title: 'head', color: TagColors.VOLCANO },
	{ title: 'director', color: TagColors.CYAN },
	{ title: 'expert', color: TagColors.BLUE },
	{ title: 'marketing', color: TagColors.LIME },
	{ title: 'business', color: TagColors.PURPLE }
];