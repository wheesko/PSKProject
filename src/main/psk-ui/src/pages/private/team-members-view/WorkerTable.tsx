import React, { useEffect, useState } from 'react';
import { Table, Popconfirm, Form, Tooltip, Button, Modal, Tag } from 'antd';
import { ExclamationCircleOutlined, DeleteOutlined, EditOutlined } from '@ant-design/icons';
import { Employee } from '../../../models/employee';
import { LearningEvent } from '../../../models/learningEvent';
import { Role } from '../../../models/role';
import { Link } from 'react-router-dom';

const { confirm } = Modal;

interface TableProps {
	employeeList: Employee[];
	columns?: any;
}

const WorkerTable: React.FunctionComponent<TableProps> = (props: TableProps) => {
	const [data, setData] = useState<Employee[]>([]);

	useEffect(() => {
		setData(props.employeeList);
		console.log(props.employeeList)
	}, [props.employeeList.length]);

	const columns = props.columns ? props.columns : [
		{
			title: 'Full name',
			dataIndex: 'id',
			key: 'id',
			render: (id: string, worker: Employee, index: number) => {
				return <Link
					to={`/profile/${worker.id}`}>{worker.name === null ? 'Worker has not finished registration' : `${worker.name} ${worker.surname}`}</Link>;
			},
		},
		{
			title: 'Email',
			dataIndex: 'email',
			key: 'email',
		},
		{
			title: 'Team',
			dataIndex: 'team',
			key: 'team',
		},
		{
			title: 'Quarter constraint',
			dataIndex: 'quarterLearningDayLimit',
			key: 'quarterLearningDayLimit',
			width: '8%'
		},
		{
			title: 'Consecutive day constraint',
			dataIndex: 'consecutiveLearningDayLimit',
			key: 'consecutiveLearningDayLimit',
			width: '8%'
		},
		{
			title: 'Role',
			dataIndex: 'role',
			key: 'role',
			render: (role: Role) => {
				return <Tag color={role.color}>{role.title}</Tag>;

			}
		},
		{
			title: 'Goals',
			key: 'goals',
			dataIndex: 'goals',
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

	return (
		<Table
			bordered
			dataSource={data}
			columns={columns}
		/>
	);
};

export { WorkerTable };
