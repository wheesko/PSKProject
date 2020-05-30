export interface RegisterWorkerResponse {
	name: string;
	comment: string;
	dateTimeAt: string;
	assignee: {
		id: number;
	};
}