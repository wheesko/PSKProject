export interface Worker {
	key: number | null,
	name: string,
	surname: string,
	role: { title: string, color: string },
	quarterConstraint: number,
	team: string,
	// learnedTopics:
	goals: string[],
	manager: string;
}