import '@toast-ui/editor/dist/toastui-editor-viewer.css';
import { Viewer } from '@toast-ui/react-editor';
import { useEffect, useRef } from 'react';
import styled from 'styled-components';

const RightViewContainer = styled.div`
  display: flex;
  flex-direction: column;
  width: 100%;
  background-color: white;
  border-radius: 8px;
  padding: 30px;
  margin: 30px 0 15px 15px;

  @media screen and (max-width: 1200px) {
    margin: 0 0 15px 0;
  }

  .right-view-body {
    border: 1px solid var(--purple-medium);
    height: 100%;
    border-radius: 9px;
    padding: 25px;
  }
  .viewer {
    border-top: 1px solid var(--purple-medium);
    margin-top: 15px;
  }
  .content-plan {
    margin-bottom: 70px;
    padding-bottom: 15px;
    font-size: 15px;
    font-weight: 500;
    color: var(--grey-dark);
  }
  @media screen and (max-width: 1200px) {
    width: 100%;
    margin: 0 0 25px 0;
    min-height: 300px;
  }
`;
const ContentViewer = ({ content }) => {
  const viewerRef = useRef();

  useEffect(() => {
    viewerRef.current && viewerRef.current.getInstance().setMarkdown(content);
  });
  return (
    <RightViewContainer>
      <div className="right-view-body">
        <span className="content-plan">프로젝트 계획을 설명해 주세요!</span>
        <div className="viewer">
          <Viewer ref={viewerRef} initialValue={content}></Viewer>
        </div>
      </div>
    </RightViewContainer>
  );
};

export default ContentViewer;
