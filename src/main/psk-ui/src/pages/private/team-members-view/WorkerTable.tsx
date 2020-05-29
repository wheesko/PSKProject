import React, { useEffect, useState } from 'react';
import { Table, Popconfirm, Form, Tooltip, Button, Modal, Tag } from 'antd';
import { ExclamationCircleOutlined, DeleteOutlined, EditOutlined } from '@ant-design/icons';
import { Employee } from '../../../models/employee';
import { LearningEvent } from '../../../models/learningEvent';
import { Role } from '../../../models/role';
import {Link} from "react-router-dom";

const { confirm } = Modal;

interface TableProps {
	employeeList: Employee[];
}

const WorkerTable: React.FunctionComponent<TableProps> = (props: TableProps) => {
	const [data, setData] = useState<Employee[]>([]);

	useEffect(() => {
		setData(props.employeeList);
	}, [props.employeeList.length]);

	const columns = [
		{
			title: 'Full name',
			dataIndex: 'id',
			key: 'id',
			editable: false,
			width: '17%',
			render: (id: string, worker: Employee, index: number) => {
				return <Link to={`/profile/${worker.id}`}>{`${worker.name} ${worker.surname}`}</Link>;
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

	return (
		<Table
			bordered
			dataSource={data}
			columns={columns}
		/>
	);
};

export { WorkerTable };
