import React, { useEffect, useState } from 'react';

import { Col, Form, Row, Select, Spin, Table, Tag, Tooltip, Typography, Progress } from 'antd';

import { RootState } from '../../../../redux';
import { useSelector } from 'react-redux';
import notificationService, { NotificationType } from '../../../../service/notification-service';
import topicService from '../../../../api/topic-service';
import { LearningTopic } from "../../../../models/learningTopic";
import '../../team-members-view/worker-topic-table/WorkerTopicTableStyles.css';
import { TeamResponse } from "../../../../api/model/team-response";
import teamService from '../../../../api/team-service';
import { InfoCircleOutlined } from "@ant-design/icons/lib";

const { Title } = Typography;

const TeamsByTopics: React.FunctionComponent<{}> = () => {
	const currentWorker = useSelector((state: RootState) => state.user);
	const [allTopics, setAllTopics] = useState<LearningTopic[]>([]);
	const [isLoading, setIsLoading] = useState<boolean>(false);
	const [selectedTopics, setSelectedTopics] = useState<string[]>([]);
	const [allTeams, setAllTeams] = useState<TeamResponse[]>([]);


	function getWorkersTopicsByManager() {
		setIsLoading(true);
		teamService.getAllTeams().then((response: TeamResponse[]) => {
			setAllTeams(response.sort((a, b) => a.name > b.name ? 1 : -1));
			setIsLoading(false);
		}).catch((error) => {
			setIsLoading(false);

			notificationService.notify({
				notificationType: NotificationType.ERROR,
				message: 'Failed to Teams',
				description: error.toString()
			});
		});
	}

	useEffect(() => {
		setIsLoading(true)
		getWorkersTopicsByManager();
		loadTopics().then(topics => {
				setAllTopics(topics.sort((a, b) => a.name > b.name ? 1 : -1));
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

	function renderLearnedPercentage(id: string, team: TeamResponse): React.ReactNode {
		let learningPercentage = getLearnedCountInTeam(id, team) > 0
			? Math.trunc((getLearnedCountInTeam(id, team) / team.workers.length) * 100)
			: 0;
		return selectedTopics.length === 0 ? <Progress percent={0}/> : <Progress percent={learningPercentage}/>
	}

	function getLearnedCountInTeam(id: string, team: TeamResponse) {
		return team.workers.filter((worker) => {
			let workersLearnedTopicNames = worker.learningDays
				.filter(learningDay => learningDay.learned)
				.map(learningDay => {
					return learningDay.topic.name
				});
			return selectedTopics.every(selectedTopic => workersLearnedTopicNames.includes(selectedTopic));
		}).length
	}

	const columns = [
		{
			title: 'Team name',
			dataIndex: 'id',
			key: 'id',
			render: (id: string, team: TeamResponse, index: number) => {
				return <Typography.Text>{team.name}</Typography.Text>
			},
		},
		{
			title: 'Employee amount',
			dataIndex: 'id',
			key: 'employeeCount',
			render: (id: string, team: TeamResponse, index: number) => {
				return team.workers.length;
			}
		},
		{
			title:
				<>
					<Tooltip title={'Select learning topics to see amount of employees that have learned a topic'}>
						<InfoCircleOutlined/>
					</Tooltip>&nbsp;
					<Typography.Text>{'Employee amount with learned topics'}</Typography.Text>
				</>,
			dataIndex: 'id',
			key: 'learnedCount',
			render: (id: string, team: TeamResponse) => {
				return selectedTopics.length === 0 ? '' :
					getLearnedCountInTeam(id, team)
			}
		},
		{
			title: 'Percentage of people learned selected topics',
			dataIndex: 'id',
			key: 'learnedPercentage',
			render: (id: string, team: TeamResponse) =>
				renderLearnedPercentage(id, team)
		}
	];

	return <>
		<Spin spinning={isLoading} size="large">
			<Row justify={"start"} className={"topic-row"}>
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
				dataSource={allTeams}
				columns={columns}
				rowKey="id" />
		</Spin>
	</>;
};

export { TeamsByTopics };