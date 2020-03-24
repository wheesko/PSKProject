import React from 'react';

import {Table, Tag, Typography} from 'antd';
import {ColumnProps} from "antd/es/table";
import namor from "namor";

import {Worker} from '../../../models/worker-model';
import './TeamMembersStyles.css'
import TagFilter from "./TagFilter";
import {YOUR_EMPLOYEES} from "../../../Constants";

const {Title} = Typography;
const TeamMembersView: React.FunctionComponent<{}> = () => {
		const roles = [{title: 'Java developer', color: 'blue'}, {
			title: 'Business analyst',
			color: 'gold'
		}, {title: 'Test engineer', color: "green"}, {
			title: 'UX engineer',
			color: 'magenta'
		}, {title: 'Software process manager', color: 'purple'}];
		const getWorkers = (): Worker[] => {
			return [...Array(15).keys()].map((key) => {
				return {
					key: key,
					name: namor.generate({words: 1, numbers: 0, saltType: 'string'}),
					surname: namor.generate({words: 1, numbers: 0}),
					role: roles[Math.floor(Math.random() * roles.length)],
					quarterConstraint: Math.floor(Math.random() * 5) + 1,
					team: namor.generate({words: 1, numbers: 0}),
					// learnedTopics:
					goals: [...Array(Math.floor(Math.random() * 5)).keys()].map((key) =>
						namor.generate({words: 1, numbers: 0})),
					manager: namor.generate({words: 2, numbers: 0})

				}
			})

		}

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
			<Title level={2} className={"teamMembersTitle"}>{YOUR_EMPLOYEES}</Title>
			{/*tag filter is breaking currently*/}
			{/*<TagFilter/>*/}
			<Table<Worker> size={"large"} dataSource={getWorkers()} columns={columns}/></>;
	}
;
export default TeamMembersView;