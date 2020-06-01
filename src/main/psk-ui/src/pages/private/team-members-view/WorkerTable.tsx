import React, { useEffect, useState } from 'react';
import { Table, Popconfirm, Form, Tooltip, Button, Modal, Tag, Typography } from 'antd';
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
	}, [props.employeeList.length]);

	const columns = props.columns ? props.columns : [
		{
			title: 'Full name',
			dataIndex: 'id',
			key: 'id',
			render: (id: string, worker: Employee, index: number) => {
				return worker.name === null ?
					<Typography.Text disabled>Worker has not finished registration</Typography.Text> :
					<Typography.Text>{`${worker.name} ${worker.surname}`}</Typography.Text>;
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
				return <Tag color={role.color}>{role.name}</Tag>;

			}
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
