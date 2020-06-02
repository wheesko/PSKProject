import React, { useEffect, useState } from 'react';

import { Col, Row, Select, Spin, Table, Tooltip, Typography, Progress, Button } from 'antd';

import { RootState } from '../../../../redux';
import { useSelector } from 'react-redux';
import notificationService, { NotificationType } from '../../../../service/notification-service';
import topicService from '../../../../api/topic-service';
import { LearningTopic } from '../../../../models/learningTopic';
import '../../team-members-view/worker-topic-table/WorkerTopicTableStyles.css';
import { TeamResponse } from '../../../../api/model/team-response';
import teamService from '../../../../api/team-service';
import { InfoCircleOutlined, DownloadOutlined } from '@ant-design/icons/lib';
import { CSVDownload, CSVLink } from 'react-csv';

const { Title } = Typography;

interface CsvTeam {
	index: number;
	teamName: string;
	teamLead: string;
	employeeAmount: number;
	learnedEmployeeAmount: number;
	percentageOfLearnedEmployees: string;
}

const TeamsByTopics: React.FunctionComponent<{}> = () => {
	const currentWorker = useSelector((state: RootState) => state.user);
	const [allTopics, setAllTopics] = useState<LearningTopic[]>([]);

	const [isLoading, setIsLoading] = useState<boolean>(false);
	const [selectedTopics, setSelectedTopics] = useState<string[]>([]);
	const [allTeams, setAllTeams] = useState<TeamResponse[]>([]);
	const [csvData, setCsvData] = useState<CsvTeam[]>([]);


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

	useEffect(() => {
		setCsvData(selectedTopics.length === 0
			? allTeams.map((team, i) => {
				return {
					index: i,
					teamName: team.name,
					teamLead: `${team.TeamLead.name} ${team.TeamLead.surname}`,
					employeeAmount: team.workers.length,
					learnedEmployeeAmount: 0,
					percentageOfLearnedEmployees: '0%'
				};
			})
			: allTeams.map((team, i) => {
				return {
					index: i,
					teamName: team.name,
					teamLead: `${team.TeamLead.name} ${team.TeamLead.surname}`,
					employeeAmount: team.workers.length,
					learnedEmployeeAmount: selectedTopics.length === 0 ? 0 : getLearnedCountInTeam(team.id, team),
					percentageOfLearnedEmployees: selectedTopics.length === 0 ? '0%' : getLearnedCountInTeam(team.id, team) > 0
						? Math.trunc((getLearnedCountInTeam(team.id, team) / team.workers.length) * 100) + '%'
						: 0 + '%'
				};
			}));
	}, [selectedTopics.length]);

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

	function renderLearnedPercentage(id: number, team: TeamResponse): React.ReactNode {
		const learningPercentage = getLearnedCountInTeam(id, team) > 0
			? Math.trunc((getLearnedCountInTeam(id, team) / team.workers.length) * 100)
			: 0;

		return selectedTopics.length === 0 ? <Progress percent={0}/> : <Progress percent={learningPercentage}/>;
	}

	function getLearnedCountInTeam(id: number, team: TeamResponse) {
		return team.workers.filter((worker) => {
			const workersLearnedTopicNames = worker.learningDays
				.filter(learningDay => learningDay.learned)
				.map(learningDay => {
					return learningDay.topic.name;
				});

			return selectedTopics.every(selectedTopic => workersLearnedTopicNames.includes(selectedTopic));
		}).length;
	}

	const columns = [
		{
			title: 'Team name',
			dataIndex: 'id',
			key: 'name',
			render: (id: number, team: TeamResponse, index: number) => {
				return <Typography.Text>{team.name}</Typography.Text>;
			},
		},
		{
			title: 'Team Lead',
			dataIndex: 'id',
			key: 'teamLead',
			render: (id: number, team: TeamResponse, index: number) => {
				return <Typography.Text>{`${team.TeamLead.name} ${team.TeamLead.surname}`}</Typography.Text>;
			},
		},
		{
			title: 'Employee amount',
			dataIndex: 'id',
			key: 'employeeCount',
			render: (id: number, team: TeamResponse, index: number) => {
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
			render: (id: number, team: TeamResponse, index: number) => {
				return selectedTopics.length === 0 ? 0 : getLearnedCountInTeam(team.id, team);
			}
		},
		{
			title: 'Percentage of people learned selected topics',
			dataIndex: 'id',
			key: 'learnedPercentage',
			render: (id: number, team: TeamResponse) =>
				renderLearnedPercentage(id, team)
		}
	];

	return <>
		<Spin spinning={isLoading} size="large">
			<Row justify={'space-between'}>
				<Typography.Title level={2}>Filter teams by learning topics</Typography.Title>
				<CSVLink
					data={csvData}
					filename={`teamsByLearningTopics[${selectedTopics.join('-')}].csv`}

					className="btn btn-primary"
					target="_blank"
				>
					<Button disabled={selectedTopics.length === 0}
						icon={<DownloadOutlined/>}>Export to
						CSV</Button>
				</CSVLink>
			</Row>
			<Row>
			</Row>
			<Row justify={'start'} className={'topic-row'}>
				<Col span={24}>
					<Select mode="tags"
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
				columns={columns}/>
		</Spin>
	</>;
};

export { TeamsByTopics };