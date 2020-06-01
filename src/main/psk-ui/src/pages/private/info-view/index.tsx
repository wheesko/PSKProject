import React, { useEffect, useState } from 'react';

import { Button, Card, Col, Row, Spin, Table, Typography } from 'antd';

import './InfoStyles.css';
import notificationService, { NotificationType } from '../../../service/notification-service';

import { LearningEvent } from '../../../models/learningEvent';
import learningDayService from '../../../api/learning-day-service';
import moment from 'moment';
import { LearningTopic } from '../../../models/learningTopic';
import { TeamsByTopics } from "./teams-by-topics";
import { ExportOutlined, DownloadOutlined } from '@ant-design/icons';
import { EXPORT_TO_CSV } from "../../../constants/otherConstants";
import { TeamResponse } from "../../../api/model/team-response";

const learnedTopicsColumns = [
	{
		title: 'Name',
		dataIndex: 'name',
		key: 'name',
	},
	{
		title: 'Description',
		dataIndex: 'description',
		key: 'description',
	}
];

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

const InfoView: React.FunctionComponent<{}> = () => {
	const [loading, setLoading] = useState<boolean>(false);
	const [teamLearningEvents, setTeamLearningEvents] = useState<LearningEvent[]>([]);

	//component did mount
	useEffect(() => {
		loadData().then(events => {
			setTeamLearningEvents(events);
			setLoading(false);
			return teamLearningEvents;
		})
			.catch((e) => {
				notificationService.notify({
					description: e.response ? e.response.data.message : '',
					notificationType: NotificationType.ERROR,
					message: 'Could not load team events'
				});
			});
	}, []);

	return <Spin spinning={loading}>
		{!loading &&
        <>
            <Row justify={"start"}>
                <Typography.Title level={2}>Your managed team info</Typography.Title>
            </Row>
            <Row gutter={[12, 24]}>
                <Col xs={24} sm={24} md={16}>
					{renderLearningDays()}
                </Col>
                <Col xs={24} sm={24} md={8}>
					{renderLearnedTopicsTable()}
                </Col>
            </Row>
            <TeamsByTopics/>
        </>
		}
	</Spin>;

	function renderLearningDays(): React.ReactNode {
		return <Card className={'table-card'}>
			<Typography.Title level={4}>Scheduled learning days</Typography.Title>
			<Table
				dataSource={teamLearningEvents
					.filter(learningEvent => !learningEvent.learned)
					.map((le, i) => {
						return { ...le, index: i }
					})}
				columns={workerLearningDayColumns}
				rowKey={"index"}
			/>
		</Card>;
	}

	function renderLearnedTopicsTable(): React.ReactNode {
		return <Card className={'table-card'}>
			<Typography.Title level={4}>Teams learned topics</Typography.Title>
			<Table
				dataSource={teamLearningEvents.filter(learningEvent => learningEvent.learned)
					//@ts-ignore
					.filter((v, i, a) => a.findIndex(t => (t.topic.name === v.topic.name)) === i)
					//@ts-ignore
					.map(learningEvent => learningEvent.topic)
				}
				rowKey="name"
				columns={learnedTopicsColumns}
			/>
		</Card>;
	}

	function loadData(): Promise<LearningEvent[]> {
		setLoading(true);
		return learningDayService.getAllLearningDaysOfTeam();
	}
};

export { InfoView };