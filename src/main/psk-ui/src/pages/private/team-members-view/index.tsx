import React from 'react';

import './TeamMembersStyles.css';
import { Table, Tag, Typography } from 'antd';
import { ColumnProps } from 'antd/es/table';

import useFetch from 'use-http';
import { Worker } from '../../../models/worker';
import { YOUR_EMPLOYEES } from '../../../Constants';
import { Role } from '../../../models/role';
import { Goal } from '../../../models/goal';

import { workerList } from '../../../tools/mockData';

const { Title } = Typography;

const TeamMembersView: React.FunctionComponent<{}> = () => {
	const workersRequest = useFetch('http://localhost:3000/workers/', []);

	console.log('loading: ', workersRequest.loading);

	const columns: ColumnProps<Worker>[] = [
		{
			title: 'Employee',
			dataIndex: 'name',
			key: 'name',
		},
		{
			title: 'Team',
			dataIndex: 'team',
			key: 'team',
		},
		{
			title: 'Quarter constraint',
			dataIndex: 'quarterConstraint',
			key: 'quarterConstraint',
		},
		{
			title: 'Role',
			dataIndex: 'role',
			key: 'role',
			render: (role: Role): React.ReactNode => {
				return (
					<Tag color={role.color} key={role.title}>
						{role.title.toUpperCase()}
					</Tag>
				);

			}
		},
		{
			title: 'Goals',
			key: 'goals',
			dataIndex: 'goals',
			render: (goals: Goal[]): React.ReactNode => (
				<span>
					{goals.map((goal: Goal) => {
						let color = goals.length > 5 ? 'geekblue' : 'green';

						if (goal.name === 'loser') {
							color = 'volcano';
						}

						return (
							<Tag color={color} key={goal.id}>
								{goal.name.toUpperCase()}
							</Tag>
						);
					})}
				</span>
			),
		},
	];

	return <>
		<Title level={2} className={'teamMembersTitle'}>{YOUR_EMPLOYEES}</Title>
		<Table<Worker> size={'large'}
			// we will use fetched data when available
			// dataSource={workersRequest.loading === true ? [] : workers}
			dataSource={workerList}
			columns={columns}
		/>
	</>;
};

export { TeamMembersView };