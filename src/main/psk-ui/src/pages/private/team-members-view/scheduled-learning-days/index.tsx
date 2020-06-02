import { LearningTopic } from "../../../../models/learningTopic";
import React from "react";
import moment from "moment";
import { Card, Table, Typography } from "antd";
import { LearningEvent } from "../../../../models/learningEvent";
import { TeamLearningEvents } from "../team-learning-events";

const workerLearningDayColumns = [
	{
		title: 'Assignee',
		dataIndex: 'assignee',
		key: 'assignee',
		render: (assignee: any) => {
			return assignee.name + ' ' + assignee.surname;
		}
	},
	{
		title: 'Name',
		dataIndex: 'name',
		key: 'name',
	},
	{
		title: 'Date',
		dataIndex: 'dateTimeAt',
		key: 'dateTimeAt',
		render: (date: string) => moment(date).format('yyyy-MM-DD')
	},
	{
		title: 'Topic',
		dataIndex: 'topic',
		key: 'topic',
		render: (topic: LearningTopic) => topic.name
	},
	{
		title: 'Topic description',
		dataIndex: 'topic',
		key: 'topic',
		render: (topic: LearningTopic) => topic.description
	}
];
const ScheduledLearningDays: React.FunctionComponent<TeamLearningEvents> = (props: TeamLearningEvents) => {
	return <Card className={'table-card'}>
		<Typography.Title level={4}>Scheduled learning days</Typography.Title>
		<Table
			dataSource={props.teamLearningEvents
				.filter(learningEvent => !learningEvent.learned)
				.map((le, i) => {
					return { ...le, index: i }
				})}
			columns={workerLearningDayColumns}
			rowKey={"index"}
		/>
	</Card>
};
export { ScheduledLearningDays }