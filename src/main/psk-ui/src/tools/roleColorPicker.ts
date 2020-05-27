import { roleToColorDictionary } from '../constants/tagColors';

export function getRoleColor(roleTitle: string): string {
	const roleKeyWords = roleTitle.split(' ');
	const role = roleToColorDictionary.find(role => {
		return roleKeyWords.some(roleKeyWord => roleKeyWord.toLowerCase() === role.name);
	});

	return role !== undefined ? role.color : '';
}