import React, { useEffect, useState } from 'react';

import { Col, Form, Row, Select, Spin, Table, Tag, Typography } from 'antd';

import { RootState } from '../../../../redux';
import { useSelector } from 'react-redux';
import { Employee } from '../../../../models/employee';
import workerService from '../../../../api/worker-service';
import notificationService, { NotificationType } from '../../../../service/notification-service';
import { Link } from 'react-router-dom';
import topicService from '../../../../api/topic-service';
import { WorkerWithTopics } from '../../../../api/model/topic-by-manager-response';
import { LearningTopic } from '../../../../models/learningTopic';
import './WorkerTopicTableStyles.css';

const { Title } = Typography;

const WorkerTopicTable: React.FunctionComponent<{}> = () => {
	const currentWorker = useSelector((state: RootState) => state.user);
	const [allTopics, setAllTopics] = useState<LearningTopic[]>([]);

	const [isLoading, setIsLoading] = useState<boolean>(false);
	const [selectedTopics, setSelectedTopics] = useState<string[]>([]);
	const [workersWithTopics, setWorkersWithTopics] = useState<WorkerWithTopics[]>([]);

	function getWorkersTopicsByManager() {
		setIsLoading(true);
		topicService.getWorkersTopicsByManager().then((response: WorkerWithTopics[]) => {
			setWorkersWithTopics(response);
			setIsLoading(false);
		}).catch((error) => {
			setIsLoading(false);

			notificationService.notify({
				notificationType: NotificationType.ERROR,
				message: 'Failed to Employees',
				description: error.toString()
			});
		});
	}

	useEffect(() => {
		getWorkersTopicsByManager();
		loadTopics().then(topics => {
			setAllTopics(topics.sort((a, b) => a.name > b.name ? 1 : -1));
			setIsLoading(false);
		}
		).catch(e => {
			notificationService.notify({
				notificationType: NotificationType.ERROR,
				message: 'Could not get topics'
			});
		});
	}, [currentWorker.managedTeamId]);

	function loadTopics(): Promise<LearningTopic[]> {
		return topicService.getAllTopics();
	}

	const onTopicSelected = (value: string) => {
		if (allTopics[allTopics.findIndex(topic => topic.name === value)])
			setSelectedTopics([...selectedTopics, allTopics[allTopics.findIndex(topic => topic.name === value)].name]);
	};

	const onDeselectedTopic = (value: string) => {
		setSelectedTopics(selectedTopics.filter(topic => topic !== value));
	};

	const columns = [
		{
			title: 'Full name',
			dataIndex: 'id',
			key: 'id',
			render: (id: string, worker: WorkerWithTopics, index: number) => {
				return worker.name === null ?
					<Typography.Text disabled>Worker has not finished registration</Typography.Text> :
					<Link
						to={`/profile/${worker.id}`}>{`${worker.name} ${worker.surname}`}</Link>;
			},
		},
		{
			title: 'Learned topics',
			dataIndex: 'topicsPast',
			render: (id: string, worker: WorkerWithTopics, index: number) => {
				return worker.topicsPast === [] ?
					null :
					worker.topicsPast.map(topic =>
						<Tag key={topic.name}>{topic.name}</Tag>
					);
			},
		},
		{
			title: 'Topics to learn in the future',
			dataIndex: 'topicsFuture',
			render: (id: string, worker: WorkerWithTopics, index: number) => {
				return worker.topicsFuture === [] ?
					null :
					worker.topicsFuture.map(topic =>
						<Tag key={topic.name}>{topic.name}</Tag>
					);
			},
		}
	];

	return <>
		<Spin spinning={isLoading} size="large">
			<Row justify={'start'} className={'topic-row'}>
				<Col span={24}>
					<Select mode="tags"
						// onChange={onTopicChange}
						onSelect={onTopicSelected}
						filterOption={(input, option) =>
								option?.children.toLowerCase().indexOf(input.toLowerCase()) >= 0
						}
						placeholder="Type in topics to filter by"
						onDeselect={onDeselectedTopic}
					>
						{allTopics.map(topic => {
							return <Select.Option key={topic.id} value={topic.name}>{topic.name}</Select.Option>;
						})}
					</Select>
				</Col>
			</Row>
			<Table
				dataSource={selectedTopics.length === 0
					? workersWithTopics
					: workersWithTopics.filter(worker =>
						// return workers who have learned selected topics in the past
						selectedTopics.every(selectedTopic => worker.topicsPast.map(topic => {
							return topic.name;
						}).includes(selectedTopic)) ||
						// return workers who will learn selected topics later
						selectedTopics.every(selectedTopic => worker.topicsFuture.map(topic => {
							return topic.name;
						}).includes(selectedTopic))
					)}
				columns={columns}/>
		</Spin>
	</>;
};

export { WorkerTopicTable };