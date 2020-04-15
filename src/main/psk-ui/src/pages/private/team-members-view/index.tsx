import React from 'react';

import { Table, Tag, Typography } from 'antd';
import { ColumnProps } from 'antd/es/table';
import useFetch from 'use-http';

import { Worker } from '../../../models/worker';
import './TeamMembersStyles.css';
import { YOUR_EMPLOYEES } from '../../../Constants';
import { workerList } from '../../../tools/mockData';

const { Title } = Typography;
const TeamMembersView: React.FunctionComponent<{}> = () => {
	const workersRequest = useFetch('http://localhost:3000/workers/', []);
	const workers = workersRequest.data;

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
			render: role => {
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
			render: goals => (
				<span>
					{goals.map((goal: string) => {
						let color = goal.length > 5 ? 'geekblue' : 'green';

						if (goal === 'loser') {
							color = 'volcano';
						}
						return (
							<Tag color={color} key={goal}>
								{goal.toUpperCase()}
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

export default TeamMembersView;