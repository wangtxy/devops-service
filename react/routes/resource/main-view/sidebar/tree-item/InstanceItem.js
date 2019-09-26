import React, { Fragment, useMemo } from 'react';
import PropTypes from 'prop-types';
import { observer } from 'mobx-react-lite';
import { injectIntl } from 'react-intl';
import { Action } from '@choerodon/master';
import { Modal } from 'choerodon-ui/pro';
import eventStopProp from '../../../../../utils/eventStopProp';
import PodCircle from '../../components/pod-circle';
import { useResourceStore } from '../../../stores';
import { useTreeItemStore } from './stores';
import { handlePromptError } from '../../../../../utils';
import { useMainStore } from '../../stores';

const stopKey = Modal.key();
const deleteKey = Modal.key();

function InstanceItem({
  record,
  name,
  podColor: {
    RUNNING_COLOR,
    PADDING_COLOR,
  },
  intlPrefix,
  intl: { formatMessage },
}) {
  const {
    treeDs,
    resourceStore,
    AppState: { currentMenuType: { id } },
    itemTypes: { IST_GROUP },
  } = useResourceStore();
  const { treeItemStore } = useTreeItemStore();
  const {
    mainStore: { openDeleteModal },
  } = useMainStore();

  const podRunningCount = record.get('podRunningCount');
  const podCount = record.get('podCount');
  const podUnlinkCount = podCount - podRunningCount;
  const podData = useMemo(() => [{
    name: 'running',
    value: podRunningCount,
    stroke: RUNNING_COLOR,
  }, {
    name: 'unlink',
    value: podUnlinkCount,
    stroke: PADDING_COLOR,
  }], [podUnlinkCount, podRunningCount, podCount]);

  function freshMenu() {
    treeDs.query();
    const { getSelectedMenu: { itemType, parentId } } = resourceStore;
    const [envId] = record.get('parentId').split('-');
    if (itemType === IST_GROUP && envId === parentId) {
      resourceStore.setUpTarget({
        type: IST_GROUP,
        id: parentId,
      });
    }
  }

  function openChangeActive(active) {
    Modal.open({
      movable: false,
      closable: false,
      key: stopKey,
      title: formatMessage({ id: `${intlPrefix}.instance.action.${active}` }),
      children: formatMessage({ id: `${intlPrefix}.instance.action.${active}.tips` }),
      onOk: () => handleChangeActive(active),
    });
  }

  async function handleChangeActive(active) {
    try {
      const istId = record.get('id');
      const result = await treeItemStore.changeIstActive(id, istId, active);
      if (handlePromptError(result, false)) {
        treeDs.query();
        resourceStore.setUpTarget({
          type: 'instances',
          id: istId,
        });
      }
    } catch (error) {
      Choerodon.handleResponseError(error);
    }
  }

  function getSuffix() {
    const istId = record.get('id');
    const istName = record.get('name');
    const [envId] = record.get('parentId').split('-');
    let actionData;
    const actionItems = {
      stop: {
        service: ['devops-service.app-service-instance.stop'],
        text: formatMessage({ id: `${intlPrefix}.instance.action.stop` }),
        action: () => openChangeActive('stop'),
      },
      start: {
        service: ['devops-service.app-service-instance.start'],
        text: formatMessage({ id: `${intlPrefix}.instance.action.start` }),
        action: () => openChangeActive('start'),
      },
      delete: {
        service: ['devops-service.app-service-instance.delete'],
        text: formatMessage({ id: `${intlPrefix}.instance.action.delete` }),
        action: () => openDeleteModal(envId, istId, istName, 'instance', freshMenu),
      },
    };
    switch (record.get('status')) {
      case 'running':
        actionData = [actionItems.stop, actionItems.delete];
        break;
      case 'stopped':
        actionData = [actionItems.start, actionItems.delete];
        break;
      case 'failed':
        actionData = [actionItems.delete];
        break;
      default:
        break;
    }

    return actionData ? <Action
      placement="bottomRight"
      data={actionData}
      onClick={eventStopProp}
    /> : null;
  }

  return <Fragment>
    <PodCircle
      size="small"
      dataSource={podData}
    />
    {name}
    {getSuffix()}
  </Fragment>;
}

InstanceItem.propTypes = {
  name: PropTypes.any,
  podColor: PropTypes.shape({}),
};

export default injectIntl(observer(InstanceItem));