import React, { useEffect, useState } from 'react';

import '../team-members-view/TeamMembersStyles.css';
import { Spin, Tag, Typography } from 'antd';

import {
	YOUR_COLLEAGUES,
} from '../../../constants/employeeConstants';
import { RootState } from '../../../redux';
import { useSelector } from 'react-redux';
import { Employee } from '../../../models/employee';
import workerService from '../../../api/worker-service';
import notificationService, { NotificationType } from '../../../service/notification-service';
import { WorkerTable } from '../team-members-view/WorkerTable';
import { Link } from "react-router-dom";
import { Role } from "../../../models/role";

const { Title } = Typography;

const WorkingTeamView: React.FunctionComponent<{}> = () => {
	const currentWorker = useSelector((state: RootState) => state.user);
	const [isLoading, setLoading] = useState<boolean>(false);
	const [myColleagues, setMyColleagues] = useState<Employee[]>([]);

	function getWorkingTeam() {
		setLoading(true);
		workerService.getColleagues().then(response => {
			setMyColleagues(response);
			setLoading(false);
		}).catch((error) => {
			setLoading(false);

			notificationService.notify({
				notificationType: NotificationType.ERROR,
				message: 'Failed to colleagues',
				description: error.toString()
			});
		});
	}

	const columns = [
		{
			title: 'Full name',
			dataIndex: 'id',
			key: 'id',
			editable: false,
			width: '17%',
			render: (id: string, worker: Employee, index: number) => {
				return worker.name === null ? 'Worker has not finished registration' : `${worker.name} ${worker.surname}`;
			},
		},
		{
			title: 'Email',
			dataIndex: 'email',
			key: 'email',
			editable: false,
			width: '15%',
		},
		{
			title: 'Team',
			dataIndex: 'team',
			key: 'team',
			editable: false,
			width: '15%',
		},
		{
			title: 'Quarter constraint',
			dataIndex: 'quarterLearningDayLimit',
			key: 'quarterLearningDayLimit',
			editable: true,
			width: '5%',
		},
		{
			title: 'Role',
			dataIndex: 'role',
			key: 'role',
			editable: false,
			width: '15%',
			render: (role: Role) => {
				return <Tag color={role.color}>{role.title}</Tag>;

			}
		},
		{
			title: 'Goals',
			key: 'goals',
			dataIndex: 'goals',
			editable: false,
			width: '20%',
			// TODO: fix displaying goals (need to create usable state interfaces)
			// render: (goals: Goal[]): React.ReactNode => (
			// 	<span>
			// 		{goals.map((goal: Goal) => {
			// 			let color = goals.length > 5 ? 'geekblue' : 'green';
			//
			// 			if (goal.name === 'loser') {
			// 				color = 'volcano';
			// 			}
			//
			// 			return (
			// 				<Tag color={color} key={goal.id}>
			// 					{goal.name.toUpperCase()}
			// 				</Tag>
			// 			);
			// 		})}
			// 	</span>
			// ),
		},
	];

	useEffect(() => {
		getWorkingTeam();
	}, [currentWorker.workingTeamId]);

	return <>
		<Title level={2} className={'teamMembersTitle'}>{YOUR_COLLEAGUES}</Title>
		<Spin spinning={isLoading} size="large">
			<WorkerTable employeeList={myColleagues} columns={columns}/>
		</Spin>
	</>;
};

export { WorkingTeamView };