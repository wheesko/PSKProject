import React, { useEffect, useState } from 'react';

import { Col, Form, Row, Select, Spin, Table, Tag, Typography } from 'antd';

import { RootState } from '../../../../redux';
import { useSelector } from 'react-redux';
import { Employee } from '../../../../models/employee';
import workerService from '../../../../api/worker-service';
import notificationService, { NotificationType } from '../../../../service/notification-service';
import { Role } from "../../../../models/role";
import { Link } from "react-router-dom";
import topicService from '../../../../api/topic-service';
import { TopicByManagerResponse } from '../../../../api/model/topic-by-manager-response';
import { LearningTopic } from "../../../../models/learningTopic";
import '../../team-members-view/worker-topic-table/WorkerTopicTableStyles.css';
import { TeamResponse } from "../../../../api/model/team-response";
import teamService from '../../../../api/team-service';

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
	console.log("All teams: ", allTeams);
	console.log("selected topics: ", selectedTopics)
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
			title: selectedTopics.length === 0
				? 'Select learning topics to see amount of employees that have learned a topic'
				: 'Employee amount with learned topics',
			dataIndex: 'id',
			key: 'learnedCount',
			render: (id: string, team: TeamResponse, index: number) => {
				return selectedTopics.length === 0 ? '' :
					team.workers.filter((worker) => {
						worker.learningDays.some(learningDay => {
							console.log("Worker: ", worker.name, "\nTeam: ", team.name, "\nTopic: ", learningDay.topic.name,"\nLearned: ",learningDay.learned)
							return learningDay.learned ? false : selectedTopics?.includes(learningDay.topic.name);
						})
					}).length
				// selectedTopics.length === 0
				// ? workersWithTopics
				// : workersWithTopics.filter(worker =>
				// 	worker.topicsPast
				// 		.some(workerTopic =>
				// 			selectedTopics?.includes(workerTopic)
				// 		))
			}
		}
		// {
		// 	title: 'Learned topics',
		// 	dataIndex: 'topicsPast',
		// 	render: (id: string, worker: TopicByManagerResponse, index: number) => {
		// 		return worker.topicsPast === [] ?
		// 			null :
		// 			worker.topicsPast.map(topic =>
		// 				<Tag>{topic}</Tag>
		// 			)
		// 	},
		// },
		// {
		// 	title: 'Topics to learn in the future',
		// 	dataIndex: 'topicsFuture',
		// 	render: (id: string, worker: TopicByManagerResponse, index: number) => {
		// 		return worker.topicsFuture === [] ?
		// 			null :
		// 			worker.topicsFuture.map(topic =>
		// 				<Tag>{topic}</Tag>
		// 			)
		// 	},
		// }
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
				dataSource={allTeams
					// selectedTopics.length === 0
					// ? workersWithTopics
					// : workersWithTopics.filter(worker =>
					// 	worker.topicsPast
					// 		.some(workerTopic =>
					// 			selectedTopics?.includes(workerTopic)
					// 		))
				}
				columns={columns}/>
		</Spin>
	</>;
};

export { TeamsByTopics };