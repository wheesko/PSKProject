import React from 'react';
import { Card, Col, Row, Typography } from 'antd';

import { connect } from 'react-redux';
import { UserState } from '../../../redux/user/types';
import { RootState } from '../../../redux';

import './ProfileStyles.css';

interface StateProps {
	user: UserState;
}

const ProfileViewComponent: React.FunctionComponent<StateProps> = (props: StateProps) => {
	const {
		user: {
			email,
			name,
			surname,
			role: {
				title
			}
		}
	} = props;

	return <>
		<Typography.Title level={2}>Hello, {name} {surname}</Typography.Title>
		{renderMyInfoCard()}
		{renderLearnedTopicsTable()}
	</>;

	function renderMyInfoCard(): React.ReactNode {
		return <Card size="small" className="user-info-card">
			<Typography.Title level={4}>Your info</Typography.Title>
			<Row>
				<Col xs={24} sm={8}>
					<Typography.Text>Email: {email}</Typography.Text>
				</Col>
				{title &&
				<Col xs={24} sm={8}>
					<Typography.Text>Role: {title}</Typography.Text>
				</Col>
				}
			</Row>
		</Card>;
	}

	function renderLearnedTopicsTable(): React.ReactNode {
		return <>
		</>;
	}
};

const mapStateToProps = (state: RootState) => {
	return {
		user: state.user
	};
};

const ProfileView = connect(mapStateToProps)(ProfileViewComponent);

export { ProfileView };