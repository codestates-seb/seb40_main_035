import styled from 'styled-components';
import { ReactComponent as LikeIcon } from '../assets/image/like.svg';
import { ReactComponent as ViewsIcon } from '../assets/image/views.svg';

const ArticleCardWrapper = styled.a`
  display: block;
  width: 323px;
  height: 286px;
  border-radius: 25px;
  background-color: #ffffff;
  transition: 300ms ease-in-out;
  position: relative;

  &:hover {
    box-shadow: 0px 4px 15px rgba(0, 0, 0, 0.2);
    transform: translate(0, -5px);
  }

  &.closed {
    &:after {
      content: '';
      display: block;
      width: 100%;
      height: 100%;
      position: absolute;
      top: 0;
      left: 0;
      border-radius: 25px;
      box-shadow: inset 0 0 0 400px rgba(0, 0, 0, 0.15);
    }
  }

  article {
    display: flex;
    flex-direction: column;
    justify-content: space-between;
    width: 100%;
    height: 100%;
    overflow: hidden;
    padding: 37px 33px;
  }

  .article-header {
    margin-bottom: 20px;
    display: -webkit-box;
    -webkit-box-orient: vertical;
    -webkit-line-clamp: 2;
    overflow: hidden;
    color: var(--black);
  }

  .project-info,
  .hashtags {
    margin-bottom: 4px;
    font-size: 15px;
    line-height: 18px;
    color: var(--grey-dark);
  }

  .project-info {
    font-weight: 500;

    p {
      margin-bottom: 4px;

      span {
        margin-right: 4px;
      }
    }

    p:last-child {
      margin-bottom: 20px;
    }
  }

  .hashtags {
    font-weight: 400;
  }

  .project-summary {
    margin-bottom: 10px;
  }

  footer {
    display: flex;
    justify-content: space-between;
    color: var(--grey-dark);

    .author {
      width: 100%;
      font-weight: 700;
      color: var(--grey-dark);
    }

    .article-info {
      display: flex;
      font-weight: 400;

      div {
        display: flex;

        svg {
          margin-right: 4px;
        }
      }

      div:first-child {
        margin-right: 4px;
      }
    }
  }
`;

const Tag = styled.li`
  display: inline-block;
  height: 26px;
  margin-right: 7px;
  padding: 4px 15px;
  border-radius: 13px;
  font-weight: 900;
  font-size: 13px;
  color: #fff;
  background-color: var(--purple);

  span {
    vertical-align: middle;
  }
`;

const ArticleCard = () => {
  return (
    <ArticleCardWrapper href="/" className="closed">
      <article>
        <h3 className="article-header">
          헬스케어 식단 관련 프로젝트를 같이 진행할 백엔드 개발자님을 구합니다
        </h3>
        <div className="project-info">
          <p>
            <span>개발 기간</span>
            2022.10.22 - 2022.11.22
          </p>
          <p>
            <span>모집 인원</span>2
          </p>
        </div>
        <div className="project-summary">
          <p className="hashtags">#온라인 #2개월</p>
          <ul>
            <Tag>
              <span>JavaScript</span>
            </Tag>
            <Tag>
              <span>Java</span>
            </Tag>
          </ul>
        </div>
        <footer>
          <a href="/" className="author">
            삼삼오오
          </a>
          <div className="article-info">
            <div>
              <LikeIcon aria-label="좋아요 수" role="img" />
              <span>10</span>
            </div>
            <div>
              <ViewsIcon aria-label="조회수" role="img" />
              <span>23</span>
            </div>
          </div>
        </footer>
      </article>
    </ArticleCardWrapper>
  );
};

export default ArticleCard;
