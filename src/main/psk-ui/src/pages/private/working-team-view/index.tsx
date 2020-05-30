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

	useEffect(() => {
		getWorkingTeam();
	}, [currentWorker.workingTeamId]);

	return <>
		<Title level={2} className={'teamMembersTitle'}>{YOUR_COLLEAGUES}</Title>
		<Spin spinning={isLoading} size="large">
			<WorkerTable employeeList={myColleagues}/>
		</Spin>
	</>;
};

export { WorkingTeamView };