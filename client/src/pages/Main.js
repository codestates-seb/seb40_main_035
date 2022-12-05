import ArticleCard from '../components/ArticleCard';
import styled from 'styled-components';
import { useState, useEffect, useCallback } from 'react';
import axios from 'axios';
import SwitchToggle from '../components/SwitchToggle';
import ScrollTopButton from '../components/ScrollTopButton';
import ArticlesGrid from '../components/ArticlesGrid';
import SkillFilterSelector from '../components/SkillFilterSelector';
import { GrCircleAlert } from 'react-icons/gr';

const Container = styled.div`
  min-height: calc(100vh - 62px);
  width: 100%;

  h1 {
    padding: 67px 0 77px;
    border-bottom: 1px solid var(--purple-medium);
  }

  .view-options {
    text-align: right;
    position: relative;
    margin: 20px 0 32px;

    .filter-options {
      display: flex;
      justify-content: flex-end;
    }

    .sort-options {
      margin: 30px 0 -5px auto;

      button {
        font-size: 15px;
        font-weight: 700;
        color: var(--grey-dark);
        margin: 5px;
        cursor: pointer;
        background-color: transparent;
        border: none;

        &.active-option {
          color: var(--purple);
        }

        &:not(:last-child)::after {
          content: '|';
          margin-left: 11px;
          color: var(--purple-medium);
        }
      }
    }
  }

  .message {
    width: 100%;
    text-align: center;

    p {
      margin-bottom: 20px;
      color: var(--purple-medium);
      font-weight: 700;
      font-size: 20px;
    }

    svg path {
      stroke: var(--purple-medium);
    }
  }
`;

// 임시 로딩 컴포넌트
const Loading = styled.div`
  width: 100%;
  height: 200px;
  padding-top: 100px;
  text-align: center;
  color: var(--purple);
  font-weight: 700;
  font-size: 20px;
  animation: blink 2s ease-in-out infinite;

  @keyframes blink {
    50% {
      opacity: 0;
    }
  }
`;

const Main = () => {
  // 아티클 목록
  const [articlesList, setArticlesList] = useState([]);
  // 전체보기/모집중만 보기 선택 토글
  const [viewAllStatus, setViewStatus] = useState(true);
  // 스킬 스택 필터
  const [filterModalDisplay, setFilterModalDisplay] = useState(false);
  const [skillfilter, setSkillFilter] = useState([]);
  // 정렬 옵션
  const sortOptions = {
    최신순: '',
    좋아요순: 'heart',
    조회순: 'view',
  };
  const [sortOption, setSortOption] = useState('최신순');
  // 데이터 로딩 상태
  const [isFetching, setIsFetching] = useState(false);
  // 데이터 요청 옵션
  const [pageNumber, setPageNumber] = useState(1);
  const [hasNextPage, setHasNextPage] = useState(true);
  const [viewPortWidth, setViewPortWidth] = useState(visualViewport.width);
  const [noArticle, setNoArticle] = useState(false);

  // 스크롤 감지
  useEffect(() => {
    const handleScroll = () => {
      const { scrollTop, offsetHeight } = document.documentElement;
      if (window.innerHeight + scrollTop >= offsetHeight) {
        setIsFetching(true);
      }
    };
    const handleResize = () => {
      let width = visualViewport.width;
      if (width <= 500) {
        width -= 15 * 2;
        console.log('-30');
      } else if (width <= 830) {
        width -= 50 * 2;
        console.log('-100');
      }
      setViewPortWidth(width);
    };
    setIsFetching(true);
    window.addEventListener('scroll', handleScroll);
    window.addEventListener('resize', handleResize);
    return () => {
      window.removeEventListener('scroll', handleScroll);
      window.removeEventListener('resize', handleResize);
    };
  }, []);

  // 필터링/정렬 변화
  useEffect(() => {
    setArticlesList([]);
    setPageNumber(1);
    setIsFetching(true);
    setHasNextPage(true);
  }, [viewAllStatus, skillfilter, sortOption]);

  // 데이터 요청 실행
  useEffect(() => {
    if (isFetching && hasNextPage) fetchArticles();
    else if (!hasNextPage) setIsFetching(false);
  }, [isFetching]);

  // 데이터 요청 콜백
  const fetchArticles = useCallback(async () => {
    const pageSize = 3 * (Math.floor((viewPortWidth + 30) / (323 + 30)) | 2);

    const skill = skillfilter.join(',');
    const status = viewAllStatus ? '' : false;
    const sort = sortOptions[sortOption];

    const { data } = await axios
      .get('/articles?', {
        params: { skill, status, page: pageNumber, sort, size: pageSize },
      })
      .catch((err) => console.error(err));

    setArticlesList(articlesList.concat(data.data));
    setPageNumber(data.pageInfo.page + 1);
    setHasNextPage(data.pageInfo.totalPages !== data.pageInfo.page);
    setIsFetching(false);
    data.data.length < 1 ? setNoArticle(true) : setNoArticle(false);
  }, [viewAllStatus, skillfilter, sortOption, pageNumber, viewPortWidth]);

  return (
    <Container>
      <h1>
        삼삼오오에서 사이드 프로젝트를 함께 할<br />
        마음 맞는 팀원을 찾아보세요!
      </h1>
      <div className="view-options">
        <div className="filter-options">
          <SwitchToggle
            right="모두 보기"
            setChecked={viewAllStatus}
            width="100px"
            onClick={() => {
              setViewStatus(!viewAllStatus);
            }}
          />
          <SkillFilterSelector
            isOpened={filterModalDisplay}
            setSkillFilter={setSkillFilter}
            setModalDisplay={() => setFilterModalDisplay(!filterModalDisplay)}
          />
        </div>
        <div className="sort-options">
          {Object.keys(sortOptions).map((option) => (
            <button
              key={option}
              className={option === sortOption ? 'active-option' : ''}
              onClick={() => setSortOption(option)}
            >
              {option}
            </button>
          ))}
        </div>
      </div>
      {noArticle && (
        <div className="message">
          <GrCircleAlert width="40px" height="40px" aria-hidden="true" />
          <p>게시글이 없습니다.</p>
        </div>
      )}
      <ArticlesGrid>
        {articlesList.map((article) => {
          return (
            <ArticleCard
              key={article.articleId}
              articleId={article.articleId}
              isCompleted={article.isCompleted}
              title={article.title}
              startDay={article.startDay}
              endDay={article.endDay}
              frontend={article.frontend}
              backend={article.backend}
              hashtags={article.hashtags}
              skills={article.skills}
              memberName={article.memberName}
              heartCount={article.heartCount}
              views={article.views}
            />
          );
        })}
      </ArticlesGrid>
      <ScrollTopButton />
      {isFetching && <Loading>게시글 불러오는 중...</Loading>}
    </Container>
  );
};

export default Main;
